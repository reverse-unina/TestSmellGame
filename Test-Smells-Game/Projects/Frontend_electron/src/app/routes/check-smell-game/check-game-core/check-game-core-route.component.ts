import {Component, NgZone, HostListener, OnInit, ViewChild} from '@angular/core';
import { CodeeditorService } from "../../../services/codeeditor/codeeditor.service";
import { ExerciseService } from "../../../services/exercise/exercise.service";
import { ActivatedRoute } from "@angular/router";
import { Question } from "../../../model/question/question.model";
import { Answer } from "../../../model/question/answer.model";
import { ExerciseConfiguration } from "../../../model/exercise/ExerciseConfiguration.model";
import { User } from "../../../model/user/user.model";
import {ElectronService} from "ngx-electron";
import { MatCheckbox } from "@angular/material/checkbox";
import { UserService } from '../../../services/user/user.service';
import { MatSnackBar } from "@angular/material/snack-bar";
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"
import {AchievementAlertComponent} from "../../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../../components/alert/fail-alert/fail-alert.component";
import {SuccessAlertComponent} from "../../../components/alert/success-alert/success-alert.component";
import {CheckSmellService} from "../../../services/check-smell/check-smell.service";
import {LeaderboardService} from "../../../services/leaderboard/leaderboard.service";

@Component({
  selector: 'app-check-game-core',
  templateUrl: './check-game-core-route.component.html',
  styleUrls: ['./check-game-core-route.component.css']
})
export class CheckGameCoreRouteComponent implements OnInit {
  exerciseName!: string;

  @ViewChild('achievementAlert') achievementAlert!: AchievementAlertComponent;
  @ViewChild('failAlert') failAlert!: FailAlertComponent;
  @ViewChild('successAlert') successAlert!: SuccessAlertComponent;

  protected checkSmellService!: CheckSmellService;
  exerciseRetrievalType!: number;

  constructor(
    private codeService: CodeeditorService,
    private exerciseService: ExerciseService,
    private route: ActivatedRoute,
    private zone: NgZone,
    private _electronService: ElectronService,
    private leaderboardService: LeaderboardService,
    private userService: UserService
  ) {
    // GET CONFIG CLASS FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveConfigFilesFromLocal',(event,data)=>{
      this.zone.run( () => {
        this.setupQuestions(data);
      })
    });

    this.exerciseName = decodeURIComponent(this.route.snapshot.params['exercise']);
    this.checkSmellService = new CheckSmellService(
      this.exerciseService,
      this.userService,
      this.leaderboardService
    )
  }

  ngOnInit(): void {
    this.exerciseRetrievalType = Number(localStorage.getItem("exerciseRetrieval"));


    // INIT PRODUCTION CLASS FROM LOCAL
    if(this.exerciseRetrievalType == 1){
      this.exerciseService.initConfigCodeFromLocal(this.exerciseName);
    } else if (this.exerciseRetrievalType == 2){
      // INIT PRODUCTION CLASS FROM CLOUD
      this.checkSmellService.initQuestionsFromCloud(this.exerciseName);
    }

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
