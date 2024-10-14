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
import { ExerciseConfiguration } from "../../../model/exercise/ExerciseConfiguration.model";
import { UserService } from '../../../services/user/user.service';

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
  exerciseConfiguration!: ExerciseConfiguration;

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

  constructor(
      private codeService: CodeeditorService,
      private exerciseService: ExerciseService,
      private route: ActivatedRoute,
      private zone: NgZone,
      private _electronService: ElectronService,
      private leaderboardService: LeaderboardService,
      private _snackBar: MatSnackBar,
      private userService: UserService
    ) { this.restoreCode(); 
        // GET COMPILE RESPONSE FROM ELECTRON
    this._electronService.ipcRenderer.on('refactoring-exercise-response', (event, data)=>{
      this.zone.run(()=>{
          this.elaborateCompilerAnswer(data);
      })
    })

    // GET PRODUCTION CLASS FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveProductionClassFromLocal',(event,data)=>{
      this.zone.run( ()=> {
        this.userCode = data
        this.originalProductionCode = data
      })
    })

    // GET TESTING CLASS FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveTestingClassFromLocal',(event,data)=>{
      this.zone.run( () => {
        this.testingCode = data
        this.originalTestCode = data
      })
    })

    // GET CONFIG FILE FROM ELECTRON
    this._electronService.ipcRenderer.on('receiveConfigFilesFromLocal',(event,data)=>{
      this.zone.run( () => {
        this.exerciseConfiguration = data;
        this.setupConfigFiles(data)
      })
    }) }

  ngOnInit(): void {
    this.compileType = Number(localStorage.getItem("compileMode"));
    this.exerciseType = Number(localStorage.getItem("exerciseRetrieval"));
    this.initSmellDescriptions();

      if (this.exerciseType == 1)
        this.initFilesFromLocal();
      else if (this.exerciseType == 2)
        this.initFilesFromCloud();
      
    }

  @HostListener('window:beforeunload', ['$event'])
    unloadHandler(event: Event) {
      this.saveCode();
  }

  initFilesFromLocal() {
    this.exerciseService.initProductionCodeFromLocal(this.exerciseName);
    this.exerciseService.initTestingCodeFromLocal(this.exerciseName);
    this.exerciseService.initConfigCodeFromLocal(this.exerciseName);
  }

  initFilesFromCloud() {
    this.exerciseService.getMainClass(this.exerciseName).subscribe( data=> {
      this.userCode = data;
      this.originalProductionCode = data;
    });
    this.exerciseService.getTestClass(this.exerciseName).subscribe( data => {
      this.testingCode = data
      this.originalTestCode = data
    })
    this.exerciseService.getConfigFile(this.exerciseName).subscribe(data=>{
      this.exerciseConfiguration = data;
      this.setupConfigFiles(data);})
  }

  ngOnDestroy(): void {
      this.saveCode();
    }

    restoreCode() {
        const savedCode = localStorage.getItem(`refactoring-game-${this.exerciseName}`);
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
        localStorage.setItem(`refactoring-game-${this.exerciseName}`, JSON.stringify(exerciseCode));
      }

      compile() {
        this.resetData();
        this.startLoading()
        this.compiledExercise = new Exercise(
                                            this.exerciseName,
                                            this.originalProductionCode,
                                            this.originalTestCode,
                                            this.testing.injectedCode);
        if(this.compileType == 1){
          this._electronService.ipcRenderer.send("compile", ([this.compiledExercise,
                                                                      this.exerciseConfiguration.refactoring_game_configuration]));
        } else if(this.compileType == 2){
          this.codeService.compile(this.compiledExercise, this.exerciseConfiguration).subscribe(data =>{
            this.elaborateCompilerAnswer(data);
          }, error => {
            this.showPopUp("Cloud server has a problem");
            this.stopLoading()
          });
        }
      }

  showPopUp(message: string){
    this._snackBar.open(message, "Close", {
      duration: 3000
    });
  }

  publishSolutionToLeaderboard(){
    this.startLoading()
    if(this.exerciseSuccess){
      this.leaderboardService.saveSolution(this.compiledExercise,
                                           this.exerciseConfiguration,
                                           this.smellNumber,
                                           Boolean(this.refactoringResult),
                                           this.originalCoverage,
                                           this.refactoredCoverage,
                                           this.smells).subscribe(result => {
        this.showPopUp("Solution saved");
        this.stopLoading();
        this.userService.getCurrentUser().subscribe((user: User | any) => {
              this.user = user;
            });
        this.userService.increaseUserExp();
        this.exerciseService.logEvent(this.user.userName, 'Completed ' + this.exerciseName + ' in refactoring game mode').subscribe({
                    next: response => console.log('Log event response:', response),
                    error: error => console.error('Error submitting log:', error)
                  });
      },error => {
        this.showPopUp("Server has a problem");
        this.stopLoading()
      });
    }else{
      this.showPopUp("To save your solution in solutions repository you have to complete the exercise");
      this.stopLoading()
    }
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

  startLoading(){
    this.progressBarMode = 'query'
  }

  stopLoading() {
    this.progressBarMode = 'determinate'
  }

  elaborateCompilerAnswer(data: any){
    this.shellCode = data.testResult
    this.smells = data.smellResult
    this.refactoringResult = data.similarityResponse
    this.exerciseSuccess = data.success
    this.originalCoverage = data.originalCoverage;
    this.refactoredCoverage = data.refactoredCoverage;
    this.stopLoading()
    if(this.exerciseSuccess){
      const json = JSON.parse(this.smells);
      this.smellList = Object.keys(json);
      this.smellResult = Object.values(json);
      for (let i=0;i<this.smellResult.length;i++){
        this.methodList.push(JSON.parse(JSON.stringify(this.smellResult[i])).methods)
        this.smellNumber+=this.methodList[i].length;
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

  setupConfigFiles(data: any){
    const json = JSON.parse(JSON.stringify(data));
    this.exerciseConfiguration.refactoring_game_configuration.refactoring_limit = json.refactoring_game_configuration.refactoring_limit;
    this.exerciseConfiguration.refactoring_game_configuration.smells_allowed = json.refactoring_game_configuration.smells_allowed;
  }


  async initSmellDescriptions() {
    // @ts-ignore
    await import('./smell_description.json').then((data) => {
      this.smellDescriptions = data.smells;
    });
  }

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
