import {Component, HostListener, NgZone, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {CodeeditorService} from "../../../services/codeeditor/codeeditor.service";
import {ExerciseService} from 'src/app/services/exercise/exercise.service';
import {LeaderboardService} from "../../../services/leaderboard/leaderboard.service";
import {ActivatedRoute, Event, Router} from '@angular/router';
import {Location} from '@angular/common';
import {firstValueFrom, Subscription} from 'rxjs';
import {MatSnackBar} from "@angular/material/snack-bar";
import {AssignmentsService} from "../../../services/assignments/assignments.service";
import {AssignmentConfiguration, Student} from '../../../model/assignment/assignment.model';
import {User} from '../../../model/user/user.model';
import {UserService} from '../../../services/user/user.service';
import {Question} from "../../../model/question/question.model";
import {RefactoringService} from "../../../services/refactoring/refactoring.service";
import {CheckSmellService} from "../../../services/check-smell/check-smell.service";
import {ElectronService} from "ngx-electron";

@Component({
  selector: 'app-assignments-core',
  templateUrl: './assignments-core-route.component.html',
  styleUrls: ['./assignments-core-route.component.css']
})
export class AssignmentsCoreRouteComponent implements OnInit, OnDestroy {
  @ViewChild('code') code: any;
  @ViewChild('testing') testing: any;
  @ViewChild('output') output: any;

  exerciseName!: string;
  routeSplit = this.location.path().split('/');
  assignment!: AssignmentConfiguration | undefined;
  assignmentName = decodeURI(this.routeSplit[this.routeSplit.length - 2]);
  checkInterval: any;
  currentUser: User | any;
  currentStudent: Student | any;
  isCompiledSuccessfully: boolean = false;

  // MESSAGES
  codeModified: boolean = false;
  private codeModifiedSubscription!: Subscription;
  serverError: string | undefined;

  refactoringService!: RefactoringService;
  checkSmellService!: CheckSmellService;

  constructor(
    private codeEditorService: CodeeditorService,
    private exerciseService: ExerciseService,
    private route: ActivatedRoute,
    private zone: NgZone,
    private _snackBar: MatSnackBar,
    private location: Location,
    private router: Router,
    private assignmentsService: AssignmentsService,
    private leaderboardService: LeaderboardService,
    private userService: UserService,
    private _electronService: ElectronService,
  ) {
    this.exerciseName = decodeURIComponent(this.route.snapshot.params['exercise']);
    this.refactoringService = new RefactoringService(
      this.codeEditorService,
      this.exerciseService,
      this.leaderboardService,
      this._snackBar,
      this.userService,
      this._electronService,
      this.zone
    );

    this.checkSmellService = new CheckSmellService(
      this.exerciseService,
      this.userService,
      this.leaderboardService
    );
  }

  async ngOnInit(): Promise<void> {
    this.currentUser = await firstValueFrom(this.userService.getCurrentUser());

    this.currentStudent = await firstValueFrom(
      this.assignmentsService.getCurrentStudentForAssignment(this.assignmentName, this.currentUser.userName)
    );

    console.log(this.assignmentName);

    this.assignment = await firstValueFrom(this.assignmentsService.getAssignmentByName(this.assignmentName));

    console.log(this.assignment);
    if (this.assignment) {
      this.startCheckInterval();
      console.log("assignemnt");
      if (this.assignment!.gameType === "refactoring") {
        this.codeModifiedSubscription = this.codeEditorService.codeModified$.subscribe(
          isModified => {
            this.codeModified = isModified;
          }
        );

        this.refactoringService.initSmellDescriptions();
        this.serverError = await this.refactoringService.initCodeFromCloud(this.exerciseName);
        this.refactoringService.restoreCode("assignment-refactoring", this.exerciseName);

        if (this.code.editorComponent && this.code.editorComponent.editor) {
          this.code.editorComponent.editor.onDidChangeModelContent(() => this.onCodeChange());
          this.testing.editorComponent.editor.onDidChangeModelContent(() => this.onCodeChange());
        }

      } else if (this.assignment!.gameType === "check-smell") {
        this.serverError = await this.checkSmellService.initQuestionsFromCloud(this.exerciseName);
      }
    }

  }


  ngOnDestroy(): void {
    if (this.codeModifiedSubscription)
      this.codeModifiedSubscription.unsubscribe();

    if (this.assignment && this.testing && this.assignment!.gameType === "refactoring")
      this.refactoringService.saveCode("assignment-refactoring", this.exerciseName, this.testing.editorComponent);

    if (this.checkInterval) {
      clearInterval(this.checkInterval);
    }
  }

  startCheckInterval() {
    const currentStudent = this.getCurrentStudent();
    if (!currentStudent) {
      console.error('Current student not found');
      return;
    }

    const endTime = this.getAssignmentEndTime(currentStudent.endDate, currentStudent.endTime);
    console.log('End time for assignment:', endTime);
    this.checkInterval = setInterval(() => {
      const currentTime = new Date();
      if (currentTime >= endTime) {
        this.zone.run(() => {
          this._snackBar.open('Tempo scaduto!', 'OK', {
            duration: 5000,
          });
          if (!this.currentStudent.consegnato)
            this.submitAssignment();
          if (this.assignment!.type === 'collaborativo')
            this.router.navigate(['/assignments/leaderboard/' + this.exerciseName]);
          else
            this.router.navigate(['/home']);
        });
        clearInterval(this.checkInterval);
      }
    }, 1000);
  }

  getAssignmentEndTime(assignmentEndDate: string, endTime: string): Date {
    const assignmentDateObj = new Date(assignmentEndDate);
    const [endHour, endMinute] = endTime.split(':').map(Number);
    assignmentDateObj.setHours(endHour, endMinute, 0, 0);
    return assignmentDateObj;
  }

  getCurrentStudent(): Student | undefined {
    return this.currentStudent;
  }

  // Check smell assignment type
  async submitExercise(): Promise<void> {
    this.checkSmellService.calculateScore();
    await this.checkSmellService.logResult(this.exerciseName, "assignment");

    this.submitCheckSmellAssignment(Math.round((this.checkSmellService.score * 100) / this.checkSmellService.assignmentScore), this.checkSmellService.assignmentScore, this.checkSmellService.questions);
  }

  submitCheckSmellAssignment: ((studentScore: number, assignmentScore: number, questions: Question[]) => void) =
    (studentScore: number, assignmentScore: number, questions: Question[]): void => {
    const studentName = this.currentStudent?.name;
    const assignmentName = this.assignment!.assignmentId;
    const exerciseId = this.exerciseName!;
    const results: string = this.generateCheckSmellReport(studentScore, assignmentScore, questions);

    if (assignmentName && studentName) {
      this.assignmentsService.submitCheckSmellAssignment(assignmentName, studentName, exerciseId, results).subscribe({
        next: () => {
          this._snackBar.open('Assignment submitted successfully', 'Close', {
            duration: 3000
          });
          this.router.navigate(['/']);
        },
        error: (err) => {
          console.error('Error submitting assignment:', err);
          return;
        }
      });
    }

    this.downloadFile(`${studentName}_${assignmentName}_results.txt`, results);
    this.currentStudent.consegnato = true;
  }

  generateCheckSmellReport: ((studentScore: number, assignmentScore: number, questions: Question[]) => string) =
    (studentScore: number, assignmentScore: number, questions: Question[]): string => {
    let content = `Student score: ${studentScore}\n`;
    content += `Assignment total score: ${assignmentScore}\n\n`;

    questions.forEach(question => {
      let questionPoints: number = 0;
      let givenPoints: number = 0;
      let lostPoints: number = 0;

      content += `Question ${questions.indexOf(question)}:\n`;

      content += "\tGiven answers: [";
      question.answers.forEach(ans => {
        if (ans.isChecked) {
          content += `${ans.answerText}, `;
          if (ans.correct)
            givenPoints += 1;
          else
            lostPoints += 0.5;
        }
      });
      content = content.substring(0, content.length-2);
      content += "]\n";

      content += "\tCorrect answers: [";
      question.answers.forEach(ans => {
        ans.correct? content += `${ans.answerText}, ` : "";
        questionPoints++;
      });
      content = content.substring(0, content.length-2);
      content += "]\n";

      content += `\tQuestion points: ${questionPoints}\n`;
      content += `\tPoint from correct answers: ${givenPoints}\n`;
      content += `\tPoints lost from incorrect answers: ${lostPoints}\n`
      content += `\tTotal points given: ${Math.min(0, givenPoints - lostPoints)}\n\n`;
    });

    return content;
  }

  readonly Math = Math;

  // Refactoring assignments type methods
  submitIsDisabled(): boolean {
    return this.refactoringService.progressBarMode == 'query' || !this.isCompiledSuccessfully || this.codeModified;
  }

  @HostListener('window:beforeunload', ['$event'])
  unloadHandler(event: Event) {
    if (this.testing && this.assignment && this.assignment!.gameType === "refactoring")
      this.refactoringService.saveCode('assignment-refactoring', this.exerciseName, this.testing.editorComponent);
  }

  submitRefactoringAssignment: (() => void) = () => {
    this.submitAssignment();
    this.saveResultsFile();
  }

  async compile(): Promise<void> {
    if (await this.refactoringService.compileExercise(this.testing.editorComponent)){
      console.log("Compile compiled");
      this.isCompiledSuccessfully = true;
      this.codeModified = false;
    }
    console.log("outside compiled");
  }

  onCodeChange() {
    this.isCompiledSuccessfully = false;
    this.codeModified = true;
  }

  submitAssignment() {
    this.refactoringService.startLoading();
    if (!this.refactoringService.exerciseIsCompiledSuccessfully) {
      this._snackBar.open('Esercizio non completato', 'Close', {
                                duration: 3000
      });
    this.refactoringService.stopLoading();
      return;
    }

    if (this.refactoringService.smellList.length <= this.refactoringService.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed) {
      const studentName = this.currentStudent?.name;
      const assignmentName = this.assignment!.assignmentId;
      const exerciseId = this.exerciseName!;
      const productionCode = this.refactoringService.userCode;
      const testCode = this.testing.editorComponent.injectedCode;
      const shellCode = this.refactoringService.shellCode;
      const results = this.generateResultsContent();

      this.exerciseService.logEvent(this.currentUser.userName, 'Completed the assignment ' + (this.assignmentName || '')).subscribe({
            next: response => console.log('Log event response:', response),
            error: error => console.error('Error submitting log:', error)
       });

      if (assignmentName && studentName) {
        this.assignmentsService.submitRefactoringAssignment(assignmentName, studentName, exerciseId, productionCode, testCode, shellCode, results).subscribe({
          next: () => {
              this._snackBar.open('Assignment submitted successfully', 'Close', {
                  duration: 3000
              });
              this.refactoringService.stopLoading();
              this.router.navigate(['/']);
          },
          error: (err) => {
              this.refactoringService.stopLoading();
              console.error('Error submitting assignment:', err);
              return;
          }
        });
      }

      this.downloadFile(`${studentName}_ClassCode.java`, this.refactoringService.userCode);
      this.downloadFile(`${studentName}_TestCode.java`, this.testing.editorComponent.injectedCode);
      this.downloadFile(`${studentName}_ShellCode.java`, this.refactoringService.shellCode);
      this.currentStudent.consegnato = true;

      if (this.assignment!.type === 'collaborativo')
       this.publishSolutionToLeaderboard();
      else
        this.router.navigate(['/']);

    } else {
        this.refactoringService.showPopUp("To submit your solution you have to complete the exercise");
        this.refactoringService.stopLoading();
    }
  }

  downloadFile(filename: string, content: string) {
    const element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(content));
    element.setAttribute('download', filename);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
  }

  publishSolutionToLeaderboard(){
    this.refactoringService.startLoading()
    if(this.refactoringService.exerciseIsCompiledSuccessfully){
      this.leaderboardService.saveSolution(this.refactoringService.compiledExercise,
                                           this.refactoringService.exerciseConfiguration,
                                           this.refactoringService.smellNumber,
                                           Boolean(this.refactoringService.refactoringResult),
                                           this.refactoringService.originalCoverage,
                                           this.refactoringService.refactoredCoverage,
                                           this.refactoringService.smells).subscribe(result => {
        this.refactoringService.stopLoading()
      },error => {
        this.refactoringService.showPopUp("Server has a problem");
        this.refactoringService.stopLoading()
      });
    }else{
      this.refactoringService.stopLoading()
    }
  }

  generateResultsContent(): string {
    let content = `Score: ${this.refactoringService.smellNumber}\n\n`;

    if (this.refactoringService.refactoringResult !== undefined) {
      content += `Refactoring result: ${this.refactoringService.refactoringResult}\n`;
      content += `Original coverage: ${this.refactoringService.originalCoverage}\n`;
      content += `Refactored coverage: ${this.refactoringService.refactoredCoverage}\n\n`;
    }

    if (this.refactoringService.smellNumberWarning) {
      content += `Smells allowed: ${this.refactoringService.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed}\n`;
      content += `Your refactored code has more smells (${this.refactoringService.smellNumber}) than the minimum accepted\n\n`;
    }

    content += "Smells:\n";
    for (let i = 0; i < this.refactoringService.smellList.length; i++) {
      content += `${this.refactoringService.smellList[i]}: ${this.refactoringService.methodList[i].length}\n`;
      content += `${this.refactoringService.smellDescriptions[this.getSmellNumber(this.refactoringService.smellList[i])].smellDescription}\n`;
      for (let j = 0; j < this.refactoringService.methodList[i].length; j++) {
        content += `${this.refactoringService.methodList[i][j]}\n`;
      }
      content += "\n";
    }

    return content;
  }

  saveResultsFile() {
    if (this.refactoringService.exerciseIsCompiledSuccessfully) {
      const studentName = this.currentStudent ? this.currentStudent.name : "";
      const filename = `${studentName}_results.txt`;
      const content = this.generateResultsContent();

      const blob = new Blob([content], { type: 'text/plain' });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = filename;
      link.click();
      window.URL.revokeObjectURL(url);
    }
  }

  getSmellNumber(smell: string) {
    switch (smell){
      case 'Assertion Roulette':
        return 0;
      case 'Conditional Test Logic':
        return 1;
      case 'Constructor Initialization':
        return 2;
      case 'Default Test':
        return 3;
      case 'Duplicate Assert':
        return 4;
      case 'Eager Test':
        return 5;
      case 'Empty Test':
        return 6;
      case 'Exception Handling':
        return 7;
      case 'General Fixture':
        return 8;
      case 'Ignored Test':
        return 9;
      case 'Lazy Test':
        return 10;
      case 'Magic Number Test':
        return 11;
      case 'Mystery Guest':
        return 12;
      case 'Print Statement':
        return 13;
      case 'Redundant Assertion':
        return 14;
      case 'Resource Optimism':
        return 15;
      case 'Sensitive Equality':
        return 16;
      case 'Sleepy Test':
        return 17;
      case 'Unknown Test':
        return 18;
      default:
        return 19;
    }
  }

}
