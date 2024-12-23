import {Component, HostListener, Input, NgZone, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ProgressBarMode} from "@angular/material/progress-bar";
import {RefactoringGameExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";
import {SmellDescription} from "../../model/SmellDescription/SmellDescription.model";
import {ActivatedRoute, Event} from "@angular/router";
import {CodeeditorService} from "../../services/codeeditor/codeeditor.service";
import {ExerciseService} from "../../services/exercise/exercise.service";
import {LeaderboardService} from "../../services/leaderboard/leaderboard.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-refactoring-core',
  templateUrl: './refactoring-core.component.html',
  styleUrls: ['./refactoring-core.component.css']
})
export class RefactoringCoreComponent /*implements OnInit, OnDestroy*/ {
  @ViewChild('code') code: any;
  @ViewChild('testing') testing: any;
  @ViewChild('output') output: any;

  @Input() interfaceFor: string = "game";
  @Input() exerciseName: string = "";
  @Input() userCode: string = "";
  @Input() progressBarMode: ProgressBarMode = "determinate";
  @Input() testingCode: string = "";
  @Input() shellCode: string = "";
  @Input() exerciseSuccess: boolean = false;
  @Input() refactoringWarning: boolean = false;
  @Input() smellNumber: number = 0;
  @Input() refactoringResult: string = "";
  @Input() originalCoverage: number = -1;
  @Input() refactoredCoverage: number = -1;
  @Input() smellNumberWarning: boolean = false;
  @Input() exerciseConfiguration!: RefactoringGameExerciseConfiguration;
  @Input() smellList: string[] = [];
  @Input() methodList: string[] = [];
  @Input() smellDescriptions: SmellDescription[] = [];
  @Input() solutionRepoRoute: string = "";

  @Input() compileCode: (() => void) = (): void => {return;};
  @Input() submitExercise: (() => void) = (): void => {return;};
  @Input() submitIsEnabled: (() => boolean) = (): boolean => {return false;};
  @Input() endButtonIsEnabled: (() => boolean) = (): boolean => {return true;};

  @Input() finishButtonText: string = "";
  @Input() endButtonText: string = "";

  originalProductionCode = ""
  originalTestCode = ""
  smells: string = "";
  smellResult: string[] = [];


  /*

  constructor(
    private codeService: CodeeditorService,
    private exerciseService: ExerciseService,
    private route: ActivatedRoute,
    private zone: NgZone,
    private leaderboardService: LeaderboardService,
    private _snackBar: MatSnackBar,
    private userService: UserService
  ) { this.restoreCode(); }

  ngOnInit(): void {
    //this.initSmellDescriptions();

    // INIT CODE FROM CLOUD
    this.exerciseService.getMainClass(this.exerciseName).subscribe( data=> {
      this.userCode = data;
      this.originalProductionCode = data;
    });
    this.exerciseService.getTestClass(this.exerciseName).subscribe( data => {
      this.testingCode = data
      this.originalTestCode = data
    });
    this.exerciseService.getRefactoringGameConfigFile(this.exerciseName).subscribe(data=>{
      this.exerciseConfiguration = data;
      this.setupConfigFiles(data);
    });
  }

  ngOnDestroy(): void {
    this.saveCode();
  }

  @HostListener('window:beforeunload', ['$event'])
  unloadHandler(event: Event) {
    this.saveCode();
  }

  /*
  async initSmellDescriptions() {
    // @ts-ignore
    await import('../../refactoring-game/refactoring-game-core/smell_description.json').then((data) => {
      this.smellDescriptions = data.smells;
    });
  }

   */

  /*
  setupConfigFiles(data: any){
    this.exerciseConfiguration.refactoring_game_configuration.refactoring_limit = data.refactoring_game_configuration.refactoring_limit;
    this.exerciseConfiguration.refactoring_game_configuration.smells_allowed = data.refactoring_game_configuration.smells_allowed;
  }

  restoreCode() {
    const savedCode = localStorage.getItem(`refactoring-${this.interfaceFor}-${this.exerciseName}`);
    if (savedCode) {
      const { productionCode, testCode } = JSON.parse(savedCode);
      this.userCode = productionCode;
      this.testingCode = testCode;
      this.originalProductionCode = productionCode;
    } else {
      this.exerciseService.getMainClass(this.exerciseName).subscribe(data => {
        this.userCode = data;
        this.originalProductionCode = data;
      });
      this.exerciseService.getTestClass(this.exerciseName).subscribe(data => {
        this.testingCode = data;
        this.originalTestCode = data;
      });
    }
  }

  saveCode() {
    const exerciseCode = {
      productionCode: this.originalProductionCode,
      testCode: this.testing.injectedCode
    };
    localStorage.setItem(`refactoring-${this.interfaceFor}-${this.exerciseName}`, JSON.stringify(exerciseCode));
  }

  elaborateCompilerAnswer(data: any) {
    this.shellCode = data.testResult;
    this.smells = data.smellResult;
    this.refactoringResult = data.similarityResponse;
    this.exerciseSuccess = data.success;
    this.originalCoverage = data.originalCoverage;
    this.refactoredCoverage = data.refactoredCoverage;
    this.stopLoading();
    if (this.exerciseSuccess) {
      const json = JSON.parse(this.smells);
      this.smellList = Object.keys(json);
      this.smellResult = Object.values(json);
      for (let i = 0; i < this.smellResult.length; i++) {
        this.methodList.push(JSON.parse(JSON.stringify(this.smellResult[i])).methods);
        this.smellNumber += this.methodList[i].length;
      }
      this.checkConfiguration();
    }
  }

  checkConfiguration(){
    if(this.refactoringResult.toString() == 'false')
      this.refactoringWarning = true
    if(this.exerciseConfiguration.refactoring_game_configuration.smells_allowed < this.smellNumber)
      this.smellNumberWarning = true;
  }

  startLoading(){
    this.progressBarMode = 'query'
  }

  stopLoading() {
    this.progressBarMode = 'determinate'
  }

  resetData(){
    this.shellCode = ""
    this.smells = ""
    this.refactoringResult = ""
    this.smellList = []
    this.smellResult = []
    this.methodList = [];
    this.smellNumber = 0
    this.exerciseSuccess = false;
    this.smellNumberWarning = false;
    this.refactoringWarning = false;
    this.originalCoverage = -1;
    this.refactoredCoverage = -1;
  }

  showPopUp(message: string) {
    this._snackBar.open(message, "Close", {
      duration: 3000
    });
  }
  */

  getSmellNumber(smell: string) {
    switch (smell){
      case 'Assertion Roulette':
        return 0;
      case 'Conditional Test Logic':
        return 1;
      case 'Constructor Initialization':
        return 2;
      case 'Default Test':
        return 3;
      case 'Duplicate Assert':
        return 4;
      case 'Eager Test':
        return 5;
      case 'Empty Test':
        return 6;
      case 'Exception Handling':
        return 7;
      case 'General Fixture':
        return 8;
      case 'Ignored Test':
        return 9;
      case 'Lazy Test':
        return 10;
      case 'Magic Number Test':
        return 11;
      case 'Mystery Guest':
        return 12;
      case 'Print Statement':
        return 13;
      case 'Redundant Assertion':
        return 14;
      case 'Resource Optimism':
        return 15;
      case 'Sensitive Equality':
        return 16;
      case 'Sleepy Test':
        return 17;
      case 'Unknown Test':
        return 18;
      default:
        return 19;
    }
  }
}
