import {Component, NgZone, OnInit, ViewChild} from '@angular/core';
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
import {ElectronService} from "ngx-electron";

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

  checkSmellService!: CheckSmellService;
  refactoringService!: RefactoringService;
  learningService!: LearningService;

  constructor(
    private route: ActivatedRoute,
    private missionService: MissionService,
    private exerciseService: ExerciseService,
    private userService: UserService,
    private codeService: CodeeditorService,
    private leaderboardService: LeaderboardService,
    private snackBar: MatSnackBar,
    private _electronService: ElectronService,
    private zone: NgZone
  ) {
    this.missionId = decodeURIComponent(this.route.snapshot.params["missionId"]);
    this.checkSmellService = new CheckSmellService(
      this.exerciseService,
      this.userService,
      this._electronService,
      this.zone
    );
    this.refactoringService = new RefactoringService(
      this.codeService,
      this.exerciseService,
      this.leaderboardService,
      this.snackBar,
      this.userService,
      this._electronService,
      this.zone
    );
    this.learningService = new LearningService(
      this.exerciseService
    );
  }

  async ngOnInit(): Promise<void> {
    try {
      this.mission = await firstValueFrom(this.missionService.getMissionById(this.missionId));
      console.log("Mission: ", this.mission);

      const userMissionsStatus: MissionStatus[] = await firstValueFrom(this.userService.getUserMissionsStatus());
      console.log("Mission status: ", userMissionsStatus);

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
    this.currentStep++;
    this.updateProgress();
    this.updateServices();
    this.completeMission();
  }

  completeMission() {
    if (this.currentStep !== this.mission.steps.length)
      return;

    let points: number = 0;
    this.mission.steps.forEach((step) => {
      step.type === "refactoring" || step.type === "check-smell"? points++ : null;
    });

    console.log("points", points);

    this.leaderboardService.updateScore(this.userService.user.value.userName, "missions", points).subscribe(
      data => {
        console.log("Updated score: ", data);
      }
    )

    this.achievementAlert.show("Holy cow, you have completed the mission", "You can see the new badge in your account page")
  }

  async updateServices(): Promise<void> {
    if (this.currentStep === this.mission.steps.length)
      return;

    switch (this.mission.steps[this.currentStep].type) {
      case "learning":
        console.log("learning");
        this.learningService = new LearningService(
          this.exerciseService
        );

        this.serverError = await this.learningService.initLearningContent(this.mission.steps[this.currentStep].id);
        break;
      case "refactoring":
        console.log("refactoring");
        this.refactoringService = new RefactoringService(
          this.codeService,
          this.exerciseService,
          this.leaderboardService,
          this.snackBar,
          this.userService,
          this._electronService,
          this.zone
        );

        this.refactoringService.initSmellDescriptions();
        this.serverError = await this.refactoringService.initCodeFromCloud(this.mission.steps[this.currentStep].id);
        break;
      case "check-smell":
        this.checkSmellService = new CheckSmellService(
          this.exerciseService,
          this.userService,
          this._electronService,
          this.zone
        );

        this.serverError = await this.checkSmellService.initQuestionsFromCloud(this.mission.steps[this.currentStep].id);
        console.log("checkService: ", this.checkSmellService.actualQuestionNumber);
    }
  }

  updateProgress() {
    this.userService.updateUserMissionStatus(this.missionId, this.currentStep).subscribe(
      success => console.log(success),
      error => console.log(error)
    )
  }

  async submitExercise(): Promise<void> {
    this.checkSmellService.calculateScore();
    this.checkSmellService.logResult(this.mission.steps[this.currentStep].id, "mission").then(
      () => {
        if (this.isExercisePassed()) {
          this.successAlert.show();
        } else {
          this.failAlert.show();
        }
      }
    );
  }

  compile(): void {
    this.refactoringService.compileExercise(this.testing.editorComponent, 1).then(
      () => {
        if (this.isExercisePassed())
          this.successAlert.show();
        else
          this.failAlert.show();
      }
    )
  }

  isExercisePassed(): boolean {
    switch (this.mission.steps[this.currentStep].type) {
      case "refactoring":
         return this.refactoringService.exerciseIsCompiledSuccessfully && this.refactoringService.smellList.length <= this.refactoringService.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed;
      case "check-smell":
         return this.checkSmellService.isExercisePassed();
      default:
        return true;
    }
  }

  readonly Math = Math;
}
