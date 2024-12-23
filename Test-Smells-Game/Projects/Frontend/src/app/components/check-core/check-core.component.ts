import {Component, HostListener, Input, OnInit} from '@angular/core';
import {Question} from "../../model/question/question.model";
import {MatCheckbox} from "@angular/material/checkbox";
import {Answer} from "../../model/question/answer.model";
import {User} from "../../model/user/user.model";
import {levelConfig} from "../../model/levelConfiguration/level.configuration.model";
import {CodeeditorService} from "../../services/codeeditor/codeeditor.service";
import {ExerciseService} from "../../services/exercise/exercise.service";
import {ActivatedRoute} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../../services/user/user.service";
import {CheckGameExerciseConfig} from "../../model/exercise/ExerciseConfiguration.model";

@Component({
  selector: 'app-check-core',
  templateUrl: './check-core.component.html',
  styleUrls: ['./check-core.component.css']
})
export class CheckCoreComponent implements OnInit{
  config!: levelConfig;
  user!: User;
  exerciseName = this.route.snapshot.params['exercise'];
  exerciseRetrievalType!: number;
  actualQuestionNumber: number = 0;

  questions!: Question[];
  exerciseConfiguration!: CheckGameExerciseConfig;

  exerciseCompleted: boolean = false;
  score: number = 0;
  assignmentScore: number = 0;

  @Input() handleSubmit?: ((studentScore: number, assignmentScore: number, questions: Question[]) => void);

  constructor(
    private codeService: CodeeditorService,
    private exerciseService: ExerciseService,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.exerciseRetrievalType = Number(localStorage.getItem("exerciseRetrieval"));

    this.exerciseService.getCheckGameConfigFile(this.exerciseName).subscribe(data => {
      this.setupQuestions(data);
    });

    this.exerciseService.getLevelConfig().subscribe(
      (data: levelConfig) => {
        this.config = data;
        console.log('LevelConfig:', this.config);
      },
      error => {
        console.error('Error fetching level config:', error);
      }
    );
  }

  private setupQuestions(data: any) {
    this.exerciseConfiguration = data;
    this.questions = data.check_game_configuration.questions;
  }

  @HostListener('window:beforeunload', ['$event'])
  unloadNotification($event: any) {
    $event.returnValue = true;
  }

  selectAnswer(option: Answer) {
    option.isChecked = !option.isChecked;
  }

  changeCheckbox(checkbox: MatCheckbox) {
    console.log("checked? ", checkbox.checked);
    checkbox.checked = !checkbox.checked;
  }

  goBackward() {
    if (this.actualQuestionNumber > 0) {
      this.actualQuestionNumber -= 1;
    }
  }

  goForward() {
    if (this.actualQuestionNumber < this.questions.length - 1) {
      this.actualQuestionNumber += 1;
    }
  }

  submitExercise(): void {
    this.calculateScore();
    this.showResults();

    if (this.handleSubmit)
      this.handleSubmit(Math.round((this.score * 100) / this.assignmentScore), this.assignmentScore, this.questions);
  }

  showResults() {
    this.exerciseCompleted = true;
    const message = `Hai ottenuto un punteggio di ${Math.round((this.score * 100) / this.assignmentScore)}/100\n`;

    if (Math.round((this.score * 100) / this.assignmentScore) >= this.config.answerPercentage) {
      alert(message + "Esercizio superato!");
      this.userService.increaseUserExp();
      this.userService.getCurrentUser().subscribe((user: User | any) => {
        this.user = user;
      });
      this.exerciseService.logEvent(this.user.userName, 'Completed ' + this.exerciseName + ' in check game mode').subscribe({
        next: response => console.log('Log event response:', response),
        error: error => console.error('Error submitting log:', error)
      });
    } else {
      alert(message + "Esercizio fallito!")
    }
  }

  calculateScore() {
    this.assignmentScore = 0;
    let score = 0;
    this.questions.forEach(question => {
      let currentCorrectAnswers = 0;
      let givenAnswersScore = 0;
      question.answers.forEach(ans => {
        ans.isCorrect? currentCorrectAnswers++ : 0;

        if (ans.isChecked) {
          if (ans.isCorrect)
            givenAnswersScore++;
          else
            givenAnswersScore -= 0.5;
        }
      });
      this.assignmentScore += currentCorrectAnswers;
      score += givenAnswersScore;
    });

    if (score < 0)
      score = 0;

    this.score = score;
  }

  renderResultsButton():boolean {
    if (this.actualQuestionNumber !== this.questions.length - 1)
      return false;

    let allQuestionsAnswered = true;
    this.questions.forEach(question => {
      allQuestionsAnswered &&= question.answers.some(answer => answer.isChecked);
    })

    return allQuestionsAnswered;
  }

}
