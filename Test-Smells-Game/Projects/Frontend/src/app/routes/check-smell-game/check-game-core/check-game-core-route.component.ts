import { Component, HostListener, OnInit } from '@angular/core';
import { CodeeditorService } from "../../../services/codeeditor/codeeditor.service";
import { ExerciseService } from "../../../services/exercise/exercise.service";
import { ActivatedRoute } from "@angular/router";
import { Question } from "../../../model/question/question.model";
import { Answer } from "../../../model/question/answer.model";
import { CheckGameExerciseConfig } from "../../../model/exercise/ExerciseConfiguration.model";
import { User } from "../../../model/user/user.model";
import { MatCheckbox } from "@angular/material/checkbox";
import { UserService } from '../../../services/user/user.service';
import { MatSnackBar } from "@angular/material/snack-bar";
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"

@Component({
  selector: 'app-check-game-core',
  templateUrl: './check-game-core-route.component.html',
  styleUrls: ['./check-game-core-route.component.css']
})
export class CheckGameCoreRouteComponent implements OnInit {
  config!: levelConfig;
  user!: User;
  exerciseName = this.route.snapshot.params['exercise'];
  exerciseRetrievalType!: number;
  actualQuestionNumber: number = 0;

  questions!: Question[];
  selectedAnswers: Answer[] = [];
  exerciseConfiguration!: CheckGameExerciseConfig;

  exerciseCompleted: boolean = false;
  score: number = 0;

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

  @HostListener('window:beforeunload', ['$event'])
  unloadNotification($event: any) {
    $event.returnValue = true;
  }

  private setupQuestions(data: any) {
    this.exerciseConfiguration = data;
    this.questions = data.check_game_configuration.questions;
  }

  selectAnswer(option: Answer) {
    this.selectedAnswers[this.actualQuestionNumber] = option;
    option.isChecked = !option.isChecked;
  }

  goForward() {
    if (this.actualQuestionNumber < this.questions.length - 1) {
      this.actualQuestionNumber += 1;
    }
  }

  goBackward() {
    if (this.actualQuestionNumber > 0) {
      this.actualQuestionNumber -= 1;
    }
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

  showResults() {
    this.exerciseCompleted = true;
    this.calculateScore();
    const percentageCorrect = (this.score / this.questions.length) * 100;
    const message = `Hai risposto correttamente al ${percentageCorrect}% delle domande. `;

    if (this.score >= (this.config.answerPercentage / 100) * this.questions.length) {
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

  changeCheckbox(checkbox: MatCheckbox) {
    console.log("checked? ", checkbox.checked);
    checkbox.checked = !checkbox.checked;
  }

  calculateScore() {
    this.selectedAnswers.forEach(answer => {
      if (answer.isCorrect) {
        this.score += 1;
      }
    });
  }
}
