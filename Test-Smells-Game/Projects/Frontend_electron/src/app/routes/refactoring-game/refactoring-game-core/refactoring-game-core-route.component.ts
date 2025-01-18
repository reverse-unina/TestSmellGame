import { Component, HostListener, NgZone, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { CodeeditorService } from "../../../services/codeeditor/codeeditor.service";
import { ExerciseService } from 'src/app/services/exercise/exercise.service';
import { ActivatedRoute } from '@angular/router';
import {ElectronService} from 'ngx-electron';
import { Exercise } from "../../../model/exercise/refactor-exercise.model";
import { LeaderboardService } from "../../../services/leaderboard/leaderboard.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ProgressBarMode } from "@angular/material/progress-bar";
import { SmellDescription } from "../../../model/SmellDescription/SmellDescription.model";
import { User } from "../../../model/user/user.model";
import { RefactoringGameExerciseConfiguration } from "../../../model/exercise/ExerciseConfiguration.model";
import { UserService } from '../../../services/user/user.service';
import {RefactoringService} from "../../../services/refactoring/refactoring.service";
import {AchievementAlertComponent} from "../../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../../components/alert/fail-alert/fail-alert.component";
import {SuccessAlertComponent} from "../../../components/alert/success-alert/success-alert.component";

@Component({
  selector: 'app-refactoring-game-core',
  templateUrl: './refactoring-game-core-route.component.html',
  styleUrls: ['./refactoring-game-core-route.component.css']
})
export class RefactoringGameCoreRouteComponent implements OnInit, OnDestroy {
  @ViewChild('code') code: any;
  @ViewChild('testing') testing: any;
  @ViewChild('output') output: any;

  @ViewChild('achievementAlert') achievementAlert!: AchievementAlertComponent;
  @ViewChild('failAlert') failAlert!: FailAlertComponent;
  @ViewChild('successAlert') successAlert!: SuccessAlertComponent;

  exerciseName!: string;
  refactoringService!: RefactoringService;
  serverError: string | undefined;
  compileType!: number;
  exerciseType!: number;

  constructor(
      private codeService: CodeeditorService,
      private exerciseService: ExerciseService,
      private route: ActivatedRoute,
      private leaderboardService: LeaderboardService,
      private snackBar: MatSnackBar,
      private userService: UserService,
      private _electronService: ElectronService,
      private zone: NgZone
    ) {
    this.refactoringService = new RefactoringService(
      this.codeService,
      this.exerciseService,
      this.leaderboardService,
      this.snackBar,
      this.userService,
      this._electronService,
      this.zone
    );
    this.exerciseName = decodeURIComponent(this.route.snapshot.params['exercise']);

    this._electronService.ipcRenderer.on('refactoring-exercise-response', (event, data)=>{
      this.zone.run(()=>{
        this.refactoringService.elaborateCompilerAnswer(data);
      })
    });

    // GET PRODUCTION CLASS FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveProductionClassFromLocal',(event,data)=>{
      this.zone.run( ()=> {
        this.refactoringService.userCode = data
        this.refactoringService.originalProductionCode = data
      })
    });

    // GET TESTING CLASS FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveTestingClassFromLocal',(event,data: string)=>{
      this.zone.run( () => {
        this.refactoringService.testingCode = data
        this.refactoringService.originalTestCode = data
      })
    });

    // GET CONFIG FILE FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveRefactoringGameConfigFromLocal',(event,data: RefactoringGameExerciseConfiguration)=>{
      console.log("Config Code from Local: ", data);
      this.zone.run( () => {
        this.refactoringService.exerciseConfiguration = RefactoringGameExerciseConfiguration.fromJson(data);
      })
    });
  }

  async ngOnInit(): Promise<void> {
    this.compileType = Number(localStorage.getItem("compileMode"));
    this.exerciseType = Number(localStorage.getItem("exerciseRetrieval"));

    if (this.exerciseType == 1) {
      await this.refactoringService.initSmellDescriptions();
      this.serverError = await this.refactoringService.initCodeFromLocal(this.exerciseName);
    } else if (this.exerciseType == 2) {
      await this.refactoringService.initSmellDescriptions();
      this.serverError = await this.refactoringService.initCodeFromCloud(this.exerciseName);
    }

    this.refactoringService.restoreCode("refactoring-game", this.exerciseName);

    console.log("Config code: ",this.refactoringService.exerciseConfiguration);
  }

  ngOnDestroy(): void {
    if (this.testing)
      this.refactoringService.saveCode("refactoring-game", this.exerciseName, this.testing.editorComponent);

    //this.router.navigate(['refactoring-game']);
  }

  @HostListener('window:beforeunload', ['$event'])
  unloadHandler(event: Event) {
    this.refactoringService.saveCode("refactoring-game", this.exerciseName, this.testing.editorComponent);
  }

  submitIsDisabled():boolean {
    return this.refactoringService.progressBarMode == 'query' || !this.refactoringService.isExercisePassed();
  }

  compile(): void {
    this.refactoringService.compileExercise(this.testing.editorComponent, this.compileType).then(
      () => {
        if (this.refactoringService.isExercisePassed())
          this.successAlert.show();
        else
          this.failAlert.show();
      }
    );
  }

  publishSolution(): void {
    this.refactoringService.startLoading()

    const score: number = Math.abs(this.refactoringService.smellNumber - this.refactoringService.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed);

    if(this.refactoringService.exerciseIsCompiledSuccessfully && this.refactoringService.isExercisePassed()){
      this.leaderboardService.saveSolution(this.refactoringService.compiledExercise,
        this.refactoringService.exerciseConfiguration,
        score,
        Boolean(this.refactoringService.refactoringResult),
        this.refactoringService.originalCoverage,
        this.refactoringService.refactoredCoverage,
        this.refactoringService.smells).subscribe(
        result => {
          this.refactoringService.showPopUp("Solution saved");
          this.refactoringService.stopLoading();
          this.userService.getCurrentUser().subscribe((user: User | any) => {
            this.refactoringService.user = user;
          });

          this.leaderboardService.getScore(this.userService.user.value.userName).subscribe(
            result => {
              const currentScore = result;
              this.leaderboardService.updateBestRefactoringScore(this.userService.user.value.userName, this.exerciseName, score).subscribe(
                (updatedScore) => {
                  console.log("Current score: ", currentScore);
                  console.log("Updated score: ", updatedScore);
                  if (updatedScore.refactoringScore > currentScore.refactoringScore) {
                    this.userService.increaseUserExp(updatedScore.refactoringScore - currentScore.refactoringScore).then(
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

          this.exerciseService.logEvent(this.userService.user.value.userName, 'Completed ' + this.exerciseName + ' in refactoring game mode').subscribe({
            next: response => console.log('Log event response:', response),
            error: error => console.error('Error submitting log:', error)
          });
        },error => {
          this.refactoringService.showPopUp("Server has a problem");
          this.refactoringService.stopLoading()
        });
    }else{
      this.refactoringService.showPopUp("To save your solution in solutions repository you have to complete the exercise");
      this.refactoringService.stopLoading()
    }
  }


  readonly Math = Math;
}
