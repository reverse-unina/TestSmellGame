import { Component, HostListener, NgZone, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { CodeeditorService } from "../../../services/codeeditor/codeeditor.service";
import { ExerciseService } from 'src/app/services/exercise/exercise.service';
import { LeaderboardService } from "../../../services/leaderboard/leaderboard.service";
import {ActivatedRoute, Event, Router} from '@angular/router';
import { Location } from '@angular/common';
import { Subscription } from 'rxjs';
import { Exercise } from "../../../model/exercise/refactor-exercise.model";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ProgressBarMode } from "@angular/material/progress-bar";
import { SmellDescription } from "../../../model/SmellDescription/SmellDescription.model";
import { RefactoringGameExerciseConfiguration } from "../../../model/exercise/ExerciseConfiguration.model";
import { AssignmentsService } from "../../../services/assignments/assignments.service";
import { Assignment, Student } from '../../../model/assignment/assignment.model';
import { User } from '../../../model/user/user.model';
import { UserService } from '../../../services/user/user.service';
import {Question} from "../../../model/question/question.model";
import {Answer} from "../../../model/question/answer.model";

@Component({
  selector: 'app-assignments-core',
  templateUrl: './assignments-core-route.component.html',
  styleUrls: ['./assignments-core-route.component.css']
})
export class AssignmentsCoreRouteComponent implements OnInit, OnDestroy {
  @ViewChild('code') outerCode: any;
  @ViewChild('testing') outerTesting: any;
  @ViewChild('output') outerOutput: any;

  compiledExercise!: Exercise;
  exerciseName = this.route.snapshot.params['exercise'];
  routeSplit = this.location.path().split('/');
  assignment!: Assignment | undefined;
  assignmentName = this.routeSplit[this.routeSplit.length - 2];
  checkInterval: any;
  currentUser: User | any;
  currentStudent: Student | any;
  isCompiledSuccessfully: boolean = false;

  progressBarMode: ProgressBarMode = 'determinate';

  solutionRepoRoute:string = `/assignment/leaderboard/${this.exerciseName}`;

  // RESULT
  userCode = "";
  testingCode = "";
  shellCode = "";
  smells = "";

  refactoringResult = "";

  smellNumber: number = 0;

  // SMELL FORMATTING VARIABLES
  smellResult: string[] = [];
  smellList: string[] = [];
  methodList: string[] = [];
  exerciseConfiguration!: RefactoringGameExerciseConfiguration;

  originalProductionCode = "";
  originalTestCode = "";

  // MESSAGES
  exerciseSuccess: boolean = false;
  smellNumberWarning: boolean = false;
  refactoringWarning: boolean = false;
  smellDescriptions: SmellDescription[] = [];
  originalCoverage: number = -1;
  refactoredCoverage: number = -1;
  codeModified: boolean = false;
  private codeModifiedSubscription!: Subscription;

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
    private userService: UserService
  ) { this.restoreCode(); }

  ngOnInit(): void {
    if (this.assignment?.gameType === "refactoring") {
      this.codeModifiedSubscription = this.codeEditorService.codeModified$.subscribe(
        isModified => {
          this.codeModified = isModified;
        }
      );

      this.initSmellDescriptions();

      // INIT CODE FROM CLOUD
      this.exerciseService.getMainClass(this.exerciseName).subscribe( data=> {
        this.userCode = data;
        this.originalProductionCode = data;
      });
      this.exerciseService.getTestClass(this.exerciseName).subscribe( data => {
        this.testingCode = data
        this.originalTestCode = data
      });
      this.exerciseService.getRefactoringGameConfigFile(this.exerciseName).subscribe(data=>{
        this.exerciseConfiguration = data;
        this.setupConfigFiles(data);
      });

      this.outerCode.code.editor.onDidChangeModelContent(() => this.onCodeChange());
      this.outerTesting.testing.editor.onDidChangeModelContent(() => this.onCodeChange());
    }

    this.userService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
      this.assignmentsService.getCurrentStudentForAssignment(this.assignmentName, this.currentUser.userName)
        .subscribe(student => {
          this.currentStudent = student;
        });
    });

    this.assignmentsService.getAssignmentByName(this.assignmentName).subscribe(
      assignment => {
        this.assignment = assignment;
        if (this.assignment) {
          this.startCheckInterval();
        }
      },
      error => {
        console.log("ERRORE", error);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.codeModifiedSubscription)
      this.codeModifiedSubscription.unsubscribe();
    if (this.checkInterval) {
      clearInterval(this.checkInterval);
    }
  }

  // Check smell assignment type
  submitCheckSmellAssignment: ((studentScore: number, assignmentScore: number, questions: Question[]) => void) =
    (studentScore: number, assignmentScore: number, questions: Question[]): void => {
    const studentName = this.currentStudent?.name;
    const assignmentName = this.assignment?.name;
    const results: string = this.generateCheckSmellReport(studentScore, assignmentScore, questions);

    if (assignmentName && studentName) {
      this.assignmentsService.submitCheckSmellAssignment(assignmentName, studentName, results).subscribe({
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
          if (ans.isCorrect)
            givenPoints += 1;
          else
            lostPoints += 0.5;
        }
      });
      content = content.substring(0, content.length-2);
      content += "]\n";

      content += "\tCorrect answers: [";
      question.answers.forEach(ans => {
        ans.isCorrect? content += `${ans.answerText}, ` : "";
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

  // Refactoring assignments type methods
  submitIsEnabled: (() => boolean) = () => {
    return this.progressBarMode == 'query' || !this.isCompiledSuccessfully || this.codeModified;
  }

  submitRefactoringAssignment: (() => void) = () => {
    this.submitAssignment();
    this.saveResultsFile();
  }
  @HostListener('window:beforeunload', ['$event'])
  unloadHandler(event: Event) {
    this.saveCode();
  }

  async initSmellDescriptions() {
    // @ts-ignore
    await import('../../refactoring-game/refactoring-game-core/smell_description.json').then((data) => {
      this.smellDescriptions = data.smells;
    });
  }

  setupConfigFiles(data: any){
    this.exerciseConfiguration.refactoring_game_configuration.refactoring_limit = data.refactoring_game_configuration.refactoring_limit;
    this.exerciseConfiguration.refactoring_game_configuration.smells_allowed = data.refactoring_game_configuration.smells_allowed;
  }

  restoreCode() {
    const savedCode = localStorage.getItem(`assignment-${this.exerciseName}`);
    if (savedCode) {
      const { productionCode, testCode } = JSON.parse(savedCode);
      this.userCode = productionCode;
      this.testingCode = testCode;
      this.originalProductionCode = productionCode;
    } else {
      this.exerciseService.getMainClass(this.exerciseName).subscribe(data => {
        this.userCode = data;
        this.originalProductionCode = data;
      });
      this.exerciseService.getTestClass(this.exerciseName).subscribe(data => {
        this.testingCode = data;
        this.originalTestCode = data;
      });
    }
  }

  saveCode() {
    const exerciseCode = {
      productionCode: this.originalProductionCode,
      testCode: this.outerTesting.testing.injectedCode
    };
    localStorage.setItem(`assignment-${this.exerciseName}`, JSON.stringify(exerciseCode));
  }

  elaborateCompilerAnswer(data: any) {
    this.shellCode = data.testResult;
    this.smells = data.smellResult;
    this.refactoringResult = data.similarityResponse;
    this.exerciseSuccess = data.success;
    this.originalCoverage = data.originalCoverage;
    this.refactoredCoverage = data.refactoredCoverage;
    this.stopLoading();
    if (this.exerciseSuccess) {
      const json = JSON.parse(this.smells);
      this.smellList = Object.keys(json);
      this.smellResult = Object.values(json);
      for (let i = 0; i < this.smellResult.length; i++) {
        this.methodList.push(JSON.parse(JSON.stringify(this.smellResult[i])).methods);
        this.smellNumber += this.methodList[i].length;
      }
      this.checkConfiguration();
    }
  }

  checkConfiguration(){
    if(this.refactoringResult.toString() == 'false')
      this.refactoringWarning = true
    if(this.exerciseConfiguration.refactoring_game_configuration.smells_allowed < this.smellNumber)
      this.smellNumberWarning = true;
  }

  startLoading(){
    this.progressBarMode = 'query'
  }

  stopLoading() {
    this.progressBarMode = 'determinate'
  }

  resetData(){
    this.shellCode = ""
    this.smells = ""
    this.refactoringResult = ""
    this.smellList = []
    this.smellResult = []
    this.methodList = [];
    this.smellNumber = 0
    this.exerciseSuccess = false;
    this.smellNumberWarning = false;
    this.refactoringWarning = false;
    this.originalCoverage = -1;
    this.refactoredCoverage = -1;
  }

  showPopUp(message: string) {
    this._snackBar.open(message, "Close", {
      duration: 3000
    });
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



  compile: (() => void) = () => {
    this.resetData();
    this.startLoading();
    // @ts-ignore
    const exercise = new Exercise(this.exerciseName, this.originalProductionCode, this.originalTestCode, this.outerTesting.testing.injectedCode);
    this.compiledExercise = exercise
    this.codeEditorService.compile(exercise, this.exerciseConfiguration).subscribe(data => {
      this.elaborateCompilerAnswer(data);
      this.isCompiledSuccessfully = true;
      this.codeModified = false;
    }, error => {
      this.showPopUp("Cloud server has a problem");
      this.stopLoading();
    });
  }

  onCodeChange() {
      this.isCompiledSuccessfully = false;
      this.codeModified = true;
    }


  submitAssignment() {
      this.startLoading();
      if (!this.exerciseSuccess) {
        this._snackBar.open('Esercizio non completato', 'Close', {
                                  duration: 3000
        });
	this.stopLoading();
        return;
      }

      if (this.smellList.length <= this.exerciseConfiguration.refactoring_game_configuration.smells_allowed) {
          this.userService.increaseUserExp();
          const studentName = this.currentStudent?.name;
          const assignmentName = this.assignment?.name;

          const productionCode = this.userCode;
          const testCode = this.outerTesting.testing.injectedCode;
          const shellCode = this.shellCode;
          const results = this.generateResultsContent();

          this.exerciseService.logEvent(this.currentUser.userName, 'Completed the assignment ' + (this.assignmentName || '')).subscribe({
                next: response => console.log('Log event response:', response),
                error: error => console.error('Error submitting log:', error)
           });

          if (assignmentName && studentName) {
              this.assignmentsService.submitRefactoringAssignment(assignmentName, studentName, productionCode, testCode, shellCode, results).subscribe({
                  next: () => {
                      this._snackBar.open('Assignment submitted successfully', 'Close', {
                          duration: 3000
                      });
                      this.stopLoading();
                      this.router.navigate(['/']);
                  },
                  error: (err) => {
                      this.stopLoading();
                      console.error('Error submitting assignment:', err);
                      return;
                  }
              });
          }

        this.downloadFile(`${studentName}_ClassCode.java`, this.userCode);
        this.downloadFile(`${studentName}_TestCode.java`, this.outerTesting.testing.injectedCode);
        this.downloadFile(`${studentName}_ShellCode.java`, this.shellCode);
        this.currentStudent.consegnato = true;
        if (this.assignment?.type === 'collaborativo')
         this.publishSolutionToLeaderboard();
        else
          this.router.navigate(['/']);
      } else {
          this.showPopUp("To submit your solution you have to complete the exercise");
          this.stopLoading();
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

  downloadCodeFiles() {
    this.downloadFile(`${this.exerciseName}.java`, this.userCode);
    this.downloadFile(`${this.exerciseName}Test.java`, this.outerTesting.testing.injectedCode);
    this.downloadFile(`ShellOutput.txt`, this.shellCode);
  }



  publishSolutionToLeaderboard(){
    this.startLoading()
    if(this.exerciseSuccess){
      this.leaderboardService.saveSolution(this.compiledExercise,
                                           this.exerciseConfiguration,
                                           this.smellNumber,
                                           Boolean(this.refactoringResult),
                                           this.originalCoverage,
                                           this.refactoredCoverage,
                                           this.smells).subscribe(result => {
        this.stopLoading()
      },error => {
        this.showPopUp("Server has a problem");
        this.stopLoading()
      });
    }else{
      this.stopLoading()
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
            if (this.assignment?.type === 'collaborativo')
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

  generateResultsContent(): string {
    let content = `Score: ${this.smellNumber}\n\n`;

    if (this.refactoringResult !== undefined) {
      content += `Refactoring result: ${this.refactoringResult}\n`;
      content += `Original coverage: ${this.originalCoverage}\n`;
      content += `Refactored coverage: ${this.refactoredCoverage}\n\n`;
    }

    if (this.smellNumberWarning) {
      content += `Smells allowed: ${this.exerciseConfiguration.refactoring_game_configuration.smells_allowed}\n`;
      content += `Your refactored code has more smells (${this.smellNumber}) than the minimum accepted\n\n`;
    }

    content += "Smells:\n";
    for (let i = 0; i < this.smellList.length; i++) {
      content += `${this.smellList[i]}: ${this.methodList[i].length}\n`;
      content += `${this.smellDescriptions[this.getSmellNumber(this.smellList[i])].smellDescription}\n`;
      for (let j = 0; j < this.methodList[i].length; j++) {
        content += `${this.methodList[i][j]}\n`;
      }
      content += "\n";
    }

    return content;
  }

  saveResultsFile() {
    if (this.exerciseSuccess) {
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

}
