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

@Component({
  selector: 'app-refactoring-game-core',
  templateUrl: './refactoring-game-core-route.component.html',
  styleUrls: ['./refactoring-game-core-route.component.css']
})
export class RefactoringGameCoreRouteComponent implements OnInit, OnDestroy {
  @ViewChild('code') code: any;
  @ViewChild('testing') testing: any;
  @ViewChild('output') output: any;

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
      console.log("Test Code from Local: ", data);

      this.zone.run( ()=> {
        this.refactoringService.userCode = data
        this.refactoringService.originalProductionCode = data
      })
    });

    // GET TESTING CLASS FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveTestingClassFromLocal',(event,data: string)=>{
      console.log("Test Code from Local: ", data);
      this.zone.run( () => {
        this.refactoringService.testingCode = data
        this.refactoringService.originalTestCode = data
      })
    });

    // GET CONFIG FILE FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveRefactoringGameConfigFromLocal',(event,data: RefactoringGameExerciseConfiguration)=>{
      console.log("Test Code from Local: ", data);
      this.zone.run( () => {
        this.refactoringService.exerciseConfiguration = RefactoringGameExerciseConfiguration.fromJson(data);
      })
    });
  }

  async ngOnInit(): Promise<void> {
    this.compileType = Number(localStorage.getItem("compileMode"));
    this.exerciseType = Number(localStorage.getItem("exerciseRetrieval"));

    this.compileType = 1; //TODO: remove

    if (this.exerciseType == 1) {
      await this.refactoringService.initSmellDescriptions();
      this.serverError = await this.refactoringService.initCodeFromLocal(this.exerciseName);
    } else if (this.exerciseType == 2) {
      await this.refactoringService.initSmellDescriptions();
      this.serverError = await this.refactoringService.initCodeFromCloud(this.exerciseName);
    }

    this.refactoringService.restoreCode("refactoring-game", this.exerciseName);

    console.log("Production code: ",this.refactoringService.userCode);
    console.log("Test code: ",this.refactoringService.testingCode);
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

  submitIsEnabled():boolean {
    return this.refactoringService.progressBarMode == 'query';
  }

  compile(): void {
    this.refactoringService.compileExercise(this.testing.editorComponent, this.compileType);
  }


  readonly Math = Math;
}
