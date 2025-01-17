import {Component, HostListener, NgZone, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {CheckSmellService} from "../../../services/check-smell/check-smell.service";
import {ActivatedRoute} from "@angular/router";
import {SuccessAlertComponent} from "../../../components/alert/success-alert/success-alert.component";
import {AchievementAlertComponent} from "../../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../../components/alert/fail-alert/fail-alert.component";
import {Question} from "../../../model/question/question.model";
import {ExerciseService} from "../../../services/exercise/exercise.service";
import {UserService} from "../../../services/user/user.service";
import {LeaderboardService} from "../../../services/leaderboard/leaderboard.service";
import {ElectronService} from "ngx-electron";

@Component({
  selector: 'app-check-game-core',
  templateUrl: './check-game-core-route.component.html',
  styleUrls: ['./check-game-core-route.component.css']
})
export class CheckGameCoreRouteComponent implements OnInit, OnDestroy {
  exerciseName!: string;

  @ViewChild('achievementAlert') achievementAlert!: AchievementAlertComponent;
  @ViewChild('failAlert') failAlert!: FailAlertComponent;
  @ViewChild('successAlert') successAlert!: SuccessAlertComponent;

  checkSmellService!: CheckSmellService;
  private exerciseRetrievalType!: number;

  constructor(
    private exerciseService: ExerciseService,
    private userService: UserService,
    private route: ActivatedRoute,
    private leaderboardService: LeaderboardService,
    private _electronService: ElectronService,
    private zone: NgZone
  ) {
    this.exerciseName = decodeURIComponent(this.route.snapshot.params['exercise']);
    this.checkSmellService = new CheckSmellService(
      this.exerciseService,
      this.userService,
      this._electronService,
      this.zone
    )
  }

  async ngOnInit(): Promise<void> {
    this.exerciseRetrievalType = Number(localStorage.getItem("exerciseRetrieval"));

    // INIT PRODUCTION CLASS FROM LOCAL
    if(this.exerciseRetrievalType == 1){
      await this.checkSmellService.initQuestionsFromLocal(this.exerciseName);
      // INIT PRODUCTION CLASS FROM CLOUD
    }else if(this.exerciseRetrievalType == 2){
      await this.checkSmellService.initQuestionsFromCloud(this.exerciseName);
    }

    console.log("Injected code; ", this.checkSmellService.questions[this.checkSmellService.actualQuestionNumber].questionCode)
  }

  ngOnDestroy() {
  }

  async submitExercise(): Promise<void> {
    this.checkSmellService.calculateScore();
    this.checkSmellService.logResult(this.exerciseName, "check game").then(
      () => {
        if (this.checkSmellService.isExercisePassed()) {
          this.successAlert.show();

          if (this.userService.hasUserLevelledUp())
            this.achievementAlert.show("Level Up!", "Congratulation, you have levelled up!");

          if (this.userService.hasUserUnlockedBadge())
            this.achievementAlert.show("Badge Unblocked!", "You have unlocked a new badge, view it on your profile page!");

          this.leaderboardService.updateScore(this.userService.user.value.userName, "check-smell", 1).subscribe(
            data => {
              console.log("Rank: ", data)
            }
          );

          this.userService.increaseUserExp(1);
        } else {
          this.failAlert.show();
        }
      }
    );


  }

  readonly Math = Math;
}
