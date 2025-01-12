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

  compiledExercise !: Exercise;
  user!: User;
  exerciseName = this.route.snapshot.params['exercise'];
  serverError: string | undefined;

  progressBarMode: ProgressBarMode = 'determinate'

  // RESULT
  userCode = "";
  testingCode = "";
  shellCode =  "";
  smells = "";

  refactoringResult = "";

  smellNumber: number = 0

  // SMELL FORMATTING VARIABLES
  smellResult: string[] = [];
  smellList: string[] = [];
  methodList: string[] = []
  exerciseConfiguration!: RefactoringGameExerciseConfiguration;

  originalProductionCode = ""
  originalTestCode = ""
  compileType!: number;
  exerciseType!: number;

  // MESSAGES
  exerciseSuccess: boolean = false;
  smellNumberWarning: boolean = false;
  refactoringWarning: boolean = false;
  smellDescriptions: SmellDescription[] = [];
  originalCoverage: number = -1;
  refactoredCoverage: number = -1;
  refactoringService: RefactoringService;

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

  }

  async ngOnInit(): Promise<void> {
    this.compileType = Number(localStorage.getItem("compileMode"));
    this.exerciseType = Number(localStorage.getItem("exerciseRetrieval"));

    this.refactoringService.restoreCode("refactoring-game", this.exerciseName);

    if (this.exerciseType == 1) {
      await this.refactoringService.initSmellDescriptions();
      this.serverError = await this.refactoringService.initCodeFromCloud(this.exerciseName);
    } else if (this.exerciseType == 2) {
      await this.refactoringService.initSmellDescriptions();
      this.serverError = await this.refactoringService.initCodeFromCloud(this.exerciseName);
    }
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
    this.refactoringService.compileExercise(this.testing.editorComponent)
  }


  readonly Math = Math;
}
