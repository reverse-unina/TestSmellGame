import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {MissionService} from "../../../services/missions/mission.service";
import {
  MissionConfiguration, MissionStatus,
  MissionStep,
} from "../../../model/missions/mission.model";
import {CheckSmellService} from "../../../services/check-smell/check-smell.service";
import {RefactoringService} from "../../../services/refactoring/refactoring.service";
import {ExerciseService} from "../../../services/exercise/exercise.service";
import {UserService} from "../../../services/user/user.service";
import {AchievementAlertComponent} from "../../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../../components/alert/fail-alert/fail-alert.component";
import {SuccessAlertComponent} from "../../../components/alert/success-alert/success-alert.component";
import {CodeeditorService} from "../../../services/codeeditor/codeeditor.service";
import {LeaderboardService} from "../../../services/leaderboard/leaderboard.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {firstValueFrom, Observable} from "rxjs";
import {LearningService} from "../../../services/learning/learning.service";

@Component({
  selector: 'app-missions-core-route',
  templateUrl: './missions-core-route.component.html',
  styleUrls: ['./missions-core-route.component.css']
})
export class MissionsCoreRouteComponent implements OnInit {
  missionId!: string;
  mission!: MissionConfiguration;
  currentStep!: number;
  isLoading: boolean = true;
  serverError: string | undefined;

  @ViewChild('achievementAlert') achievementAlert!: AchievementAlertComponent;
  @ViewChild('failAlert') failAlert!: FailAlertComponent;
  @ViewChild('successAlert') successAlert!: SuccessAlertComponent;

  @ViewChild('code') code: any;
  @ViewChild('testing') testing: any;
  @ViewChild('output') output: any;

  @ViewChild('question') question: any;

  protected checkSmellService!: CheckSmellService;
  protected refactoringService!: RefactoringService;
  protected learningService!: LearningService;

  constructor(
    private route: ActivatedRoute,
    private missionService: MissionService,
    private exerciseService: ExerciseService,
    private userService: UserService,
    private codeService: CodeeditorService,
    private leaderboardService: LeaderboardService,
    private snackBar: MatSnackBar
  ) {
    this.missionId = decodeURIComponent(this.route.snapshot.params["missionId"]);
    this.checkSmellService = new CheckSmellService(
      this.exerciseService,
      this.userService,
      this.leaderboardService
    );
    this.refactoringService = new RefactoringService(
      this.codeService,
      this.exerciseService,
      this.leaderboardService,
      this.snackBar,
      this.userService
    );
    this.learningService = new LearningService(
      this.exerciseService
    );
  }

  async ngOnInit(): Promise<void> {
    try {
      this.mission = await firstValueFrom(this.missionService.getMissionById(this.missionId));
      //console.log("Mission: ", this.mission);

      const userMissionsStatus: MissionStatus[] = await firstValueFrom(this.userService.getUserMissionsStatus());
      //console.log("Mission status: ", userMissionsStatus);

      if (userMissionsStatus) {
        const missionStatus = userMissionsStatus.find(mission => mission.missionId === this.mission.id);
        if (missionStatus) {
          this.currentStep = missionStatus.steps;
        }
      }

      if (this.currentStep === undefined) {
        this.currentStep = 0;
      }

      this.updateServices();

      this.isLoading = false;
      this.serverError = undefined;
    } catch (error: any) {
      this.isLoading = false;
      this.serverError = error.error.message;
    }
  }

  nextStep() {
    if (this.mission.steps[this.currentStep].type === "learning") {
      this.updateProgress();
    }

    this.currentStep++;

    this.updateServices();
    this.completeMission();
  }

  completeMission() {
    if (this.currentStep !== this.mission.steps.length)
      return;

    let score: number = 0;
    this.mission.steps.forEach((step) => {
      step.type === "refactoring" || step.type === "check-smell"? score++ : null;
    });

    this.exerciseService.logEvent(`Mission ${this.missionId}`, this.userService.user.value.userName, "completed the mission successfully").subscribe(
      next => {
        console.log(JSON.stringify(next));
      });

    //console.log("points", score);

    this.leaderboardService.updateMissionsScore(this.userService.user.value.userName, score).subscribe(
      data => {
        //console.log("Updated score: ", data);
        this.exerciseService.logEvent(`Mission ${this.missionId}`, this.userService.user.value.userName, `increased missions game mode points by ${score}`).subscribe(
          next => {
            console.log(JSON.stringify(next));
          });
      }
    )

    this.achievementAlert.show("Holy cow, you have completed the mission", "You can see the new badge in your account page")
    this.exerciseService.logEvent(`Mission ${this.missionId}`, this.userService.user.value.userName, `unlocked a new badge`).subscribe(
      next => {
        console.log(JSON.stringify(next));
      });
  }

  async updateServices(): Promise<void> {
    if (this.currentStep === this.mission.steps.length)
      return;

    switch (this.mission.steps[this.currentStep].type) {
      case "learning":
        this.learningService = new LearningService(
          this.exerciseService
        );

        this.serverError = await this.learningService.initLearningContent(this.mission.steps[this.currentStep].id);
        break;
      case "refactoring":
        this.refactoringService = new RefactoringService(
          this.codeService,
          this.exerciseService,
          this.leaderboardService,
          this.snackBar,
          this.userService
        );

        this.refactoringService.initSmellDescriptions();
        this.serverError = await this.refactoringService.initCodeFromCloud(this.mission.steps[this.currentStep].id);
        break;
      case "check-smell":
        this.checkSmellService = new CheckSmellService(
          this.exerciseService,
          this.userService,
          this.leaderboardService
        );

        this.serverError = await this.checkSmellService.initQuestions(this.mission.steps[this.currentStep].id);
    }
  }

  updateProgress() {
    this.userService.updateUserMissionStatus(this.missionId, this.currentStep+1).subscribe(
      success => console.log(success),
      error => console.log(error)
    )
  }

  async submitCheckSmellExercise(): Promise<void> {
    this.checkSmellService.calculateScore();

    if (this.checkSmellService.config.logTries) {
      this.exerciseService.submitCheckSmellExercise(`Mission ${this.missionId} - step ${this.currentStep}`, this.userService.user.value.userName, this.mission.steps[this.currentStep].id, this.checkSmellService.generateCheckSmellReport()).subscribe(
        result => {
          console.log("Updated solution", result);
        }
      );
    }

    this.exerciseService.logEvent(`Mission ${this.missionId} - step ${this.currentStep}`, this.userService.user.value.userName, "completed check-smell exercise " + this.mission.steps[this.currentStep].id + " with score " + Math.round((this.checkSmellService.score * 100) / this.checkSmellService.assignmentScore) + '/100').subscribe(
      next => {
        console.log(JSON.stringify(next));
        if (this.isExercisePassed()) {
          this.exerciseService.logEvent(`Mission ${this.missionId} - step ${this.currentStep}`, this.userService.user.value.userName, "completed mission step successfully").subscribe(
            next => {
              console.log(JSON.stringify(next));
            });
        } else {
          this.exerciseService.logEvent(`Mission ${this.missionId} - step ${this.currentStep}`, this.userService.user.value.userName, "failed mission step").subscribe(
            next => {
              console.log(JSON.stringify(next));
            });
        }
      });

    if (this.isExercisePassed()) {
      this.successAlert.show('You have completed successfully the mission step.');
      this.updateProgress();
    } else {
      this.failAlert.show('You failed the exercise. Don’t give up, try again!');
    }
  }

  compileRefactoringExercise(): void {
    this.refactoringService.compileExercise(`Mission ${this.missionId} - step ${this.currentStep}`, this.testing.editorComponent).then(
      () => {
        if (this.isExercisePassed()) {
          this.successAlert.show('You have completed successfully the mission step.');
          this.updateProgress();

          // this.successAlert.show('You have completed successfully the mission step.');
          this.exerciseService.logEvent(`Mission ${this.missionId} - step ${this.currentStep}`, this.userService.user.value.userName, "completed mission step successfully").subscribe(
            next => {
              console.log(JSON.stringify(next));
            });
        } else {
          // this.failAlert.show('You failed the exercise. Don’t give up, try again!');
          this.exerciseService.logEvent(`Mission ${this.missionId} - step ${this.currentStep}`, this.userService.user.value.userName, "failed mission step").subscribe(
            next => {
              console.log(JSON.stringify(next));
            });
        }
      }
    )
  }

  disableCompileButton(): boolean {
    return this.refactoringService.isExercisePassed();
  }

  isExercisePassed(): boolean {
    switch (this.mission.steps[this.currentStep].type) {
      case "refactoring":
         return this.refactoringService.isExercisePassed();
      case "check-smell":
         return this.checkSmellService.isExercisePassed();
      default:
        return true;
    }
  }

  protected readonly Math = Math;
}
