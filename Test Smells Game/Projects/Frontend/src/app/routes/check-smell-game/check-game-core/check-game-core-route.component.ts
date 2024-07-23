import { Component, HostListener, OnInit } from '@angular/core';
import { CodeeditorService } from "../../../services/codeeditor/codeeditor.service";
import { ExerciseService } from "../../../services/exercise/exercise.service";
import { ActivatedRoute } from "@angular/router";
import { Question } from "../../../model/question/question.model";
import { Answer } from "../../../model/question/answer.model";
import { ExerciseConfiguration } from "../../../model/exercise/ExerciseConfiguration.model";
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
  exerciseName = this.route.snapshot.params['exercise'];
  exerciseRetrievalType!: number;
  actualQuestionNumber: number = 0;

  questions!: Question[];
  selectedAnswers: Answer[] = [];
  exerciseConfiguration!: ExerciseConfiguration;

  exerciseCompleted: boolean = false;
  score: number = 0;

  constructor(
    private codeService: CodeeditorService,
    private exerciseService: ExerciseService,
    private route: ActivatedRoute,
    private _snackBar: MatSnackBar,
    private userService: UserService
  ) { this.initLevelConfig(); }

  ngOnInit(): void {
    this.exerciseRetrievalType = Number(localStorage.getItem("exerciseRetrieval"));

    this.exerciseService.getConfigFile(this.exerciseName).subscribe(data => {
      this.setupQuestions(data);
    });
  }

  async initLevelConfig() {
                       // @ts-ignore
                       await import('src/assets/assets/level_config.json').then((data) => {
                         this.config = data;
                       });
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
    this.clearCheckboxes();
    this.selectedAnswers[this.actualQuestionNumber] = option;
    option.isChecked = true;
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

  showResults() {
    this.exerciseCompleted = true;
    this.calculateScore();
    const percentageCorrect = (this.score / this.questions.length) * 100;
    const message = `Hai risposto correttamente al ${percentageCorrect}% delle domande. `;

    if (this.score >= (this.config.answerPercentage / 100) * this.questions.length) {
      this.userService.increaseUserExp();
      alert(message + "Esercizio superato!");
    } else {
      alert(message + "Esercizio fallito!")
    }
  }

  changeCheckbox(checkbox: MatCheckbox) {
    checkbox.checked = true;
  }

  calculateScore() {
    this.selectedAnswers.forEach(answer => {
      if (answer.isCorrect) {
        this.score += 1;
      }
    });
  }

  private clearCheckboxes() {
    this.questions[this.actualQuestionNumber].answers.forEach(element => {
      element.isChecked = false;
    });
  }
}
