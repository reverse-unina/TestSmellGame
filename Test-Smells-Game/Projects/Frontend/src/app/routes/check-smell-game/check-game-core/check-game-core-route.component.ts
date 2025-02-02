import {Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {CheckSmellService} from "../../../services/check-smell/check-smell.service";
import {ActivatedRoute} from "@angular/router";
import {SuccessAlertComponent} from "../../../components/alert/success-alert/success-alert.component";
import {AchievementAlertComponent} from "../../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../../components/alert/fail-alert/fail-alert.component";
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
  exerciseCompleted: boolean = false;

  @ViewChild('achievementAlert') achievementAlert!: AchievementAlertComponent;
  @ViewChild('failAlert') failAlert!: FailAlertComponent;
  @ViewChild('successAlert') successAlert!: SuccessAlertComponent;

  protected checkSmellService!: CheckSmellService;

  constructor(
    private exerciseService: ExerciseService,
    private userService: UserService,
    private route: ActivatedRoute,
    private leaderboardService: LeaderboardService,
  ) {
    this.exerciseName = decodeURIComponent(this.route.snapshot.params['exercise']);
    this.checkSmellService = new CheckSmellService(
      this.exerciseService,
      this.userService,
      this.leaderboardService
    )
  }

  ngOnInit(): void {
    this.checkSmellService.initQuestions(this.exerciseName);
  }

  async submitExercise(): Promise<void> {
    this.exerciseCompleted = true;
    const stat = this.checkSmellService.calculateScore();

    if (this.checkSmellService.config.logTries) {
      this.exerciseService.submitCheckSmellExercise("Check game", this.userService.user.value.userName, this.exerciseName, this.checkSmellService.generateCheckSmellReport()).subscribe(
        result => {
          console.log("Updated solution", result);
        }
      );
    }

    this.leaderboardService.saveNewUserSubmitHistory(this.userService.user.value.userId, "check-smell", this.checkSmellService.isExercisePassed() ? 1 : 0, this.exerciseName).subscribe(
      result => {
        console.log("Saved user history: ", result);
      }
    )

    if (this.checkSmellService.isExercisePassed()) {
      this.successAlert.show();

      this.leaderboardService.saveCheckSmellSolution(this.exerciseName, Math.round((this.checkSmellService.score * 100) / this.checkSmellService.assignmentScore), stat[0], stat[1], stat[2]).subscribe(
        data => {
          console.log("Updated solution", data);
        }
      );

      this.leaderboardService.getScore(this.userService.user.value.userName).subscribe(
        result => {
          const currentScore = result;
          this.leaderboardService.updateBestCheckSmellScore(this.userService.user.value.userName, this.exerciseName, 1).subscribe(
            (updatedScore) => {

              //console.log("Current score: ", currentScore);
              //console.log("Updated score: ", updatedScore);
              if (updatedScore.checkSmellScore !== currentScore.checkSmellScore) {
                this.exerciseService.logEvent("Check game", this.userService.user.value.userName, "increased check-smell game mode points by 1").subscribe(
                  next => {
                    console.log(JSON.stringify(next));
                  });

                this.userService.increaseUserExp(1).then(
                  success => {
                    this.exerciseService.logEvent("Check game", this.userService.user.value.userName, "increased experience points by 1").subscribe(
                      next => {
                        console.log(JSON.stringify(next));
                      });

                    if (this.userService.hasUserLevelledUp()) {
                      this.achievementAlert.show("Level Up!", "Congratulation, you have levelled up!");
                      this.exerciseService.logEvent("Check game", this.userService.user.value.userName, "has levelled up").subscribe(
                        next => {
                          console.log(JSON.stringify(next));
                      });
                    }

                    if (this.userService.hasUserUnlockedBadge()) {
                      this.achievementAlert.show("Badge Unblocked!", "You have unlocked a new badge, view it on your profile page!");
                      this.exerciseService.logEvent("Check game", this.userService.user.value.userName, "has unlocked a new badge").subscribe(
                        next => {
                          console.log(JSON.stringify(next));
                      });
                    }
                  }
                );
              }
            }
          );

          this.exerciseService.logEvent("Check game", this.userService.user.value.userName,
            "completed check-smell exercise " + this.exerciseName + " with score " + Math.round((this.checkSmellService.score * 100) / this.checkSmellService.assignmentScore) + '/100')
            .subscribe(
              next => {
                console.log(JSON.stringify(next));
            });
        }
      );
    } else {
      this.failAlert.show();

      this.exerciseService.logEvent("Check game", this.userService.user.value.userName,
        "failed check-smell exercise " + this.exerciseName + " with score " + Math.round((this.checkSmellService.score * 100) / this.checkSmellService.assignmentScore) + '/100')
        .subscribe(
          next => {
            console.log(JSON.stringify(next));
        });
    }

  }

  protected readonly Math = Math;
}
