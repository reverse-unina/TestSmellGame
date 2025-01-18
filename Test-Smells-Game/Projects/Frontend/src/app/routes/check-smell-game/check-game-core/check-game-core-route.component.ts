import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {CheckSmellService} from "../../../services/check-smell/check-smell.service";
import {ActivatedRoute} from "@angular/router";
import {SuccessAlertComponent} from "../../../components/alert/success-alert/success-alert.component";
import {AchievementAlertComponent} from "../../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../../components/alert/fail-alert/fail-alert.component";
import {Question} from "../../../model/question/question.model";
import {ExerciseService} from "../../../services/exercise/exercise.service";
import {UserService} from "../../../services/user/user.service";
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

  constructor(
    private exerciseService: ExerciseService,
    private userService: UserService,
    private route: ActivatedRoute,
    private leaderboardService: LeaderboardService
  ) {
    this.exerciseName = decodeURIComponent(this.route.snapshot.params['exercise']);
    this.checkSmellService = new CheckSmellService(
      this.exerciseService,
      this.userService,
      this.leaderboardService
    )
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.exerciseName = params['exercise'];
      this.checkSmellService.initQuestions(this.exerciseName);
    });
  }

  async submitExercise(): Promise<void> {
    this.checkSmellService.calculateScore();
    this.checkSmellService.logResult(this.exerciseName, "check game").then(
      () => {
        if (this.checkSmellService.isExercisePassed()) {
          this.successAlert.show();

          this.leaderboardService.getScore(this.userService.user.value.userName).subscribe(
            result => {
              const currentScore = result;
              this.leaderboardService.updateBestCheckSmellScore(this.userService.user.value.userName, this.exerciseName, 1).subscribe(
                (updatedScore) => {
                  console.log("Current score: ", currentScore);
                  console.log("Updated score: ", updatedScore);
                  if (updatedScore.checkSmellScore !== currentScore.checkSmellScore) {
                    this.userService.increaseUserExp(1).then(
                      success => {
                        if (this.userService.hasUserLevelledUp())
                          this.achievementAlert.show("Level Up!", "Congratulation, you have levelled up!");

                        if (this.userService.hasUserUnlockedBadge())
                          this.achievementAlert.show("Badge Unblocked!", "You have unlocked a new badge, view it on your profile page!");
                      }
                    );
                  }
                }
              );
            }
          );
        } else {
          this.failAlert.show();
        }
      }
    );



  }

  protected readonly Math = Math;
}
