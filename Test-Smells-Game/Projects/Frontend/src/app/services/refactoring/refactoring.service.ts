// core/refactoring.service.ts
import {Injectable, ViewChild} from '@angular/core';
import {SmellDescription} from "../../model/SmellDescription/SmellDescription.model";
import {RefactoringGameExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";
import {CodeeditorService} from "../codeeditor/codeeditor.service";
import {ExerciseService} from "../exercise/exercise.service";
import {LeaderboardService} from "../leaderboard/leaderboard.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../user/user.service";
import {Exercise} from "../../model/exercise/refactor-exercise.model";
import {User} from "../../model/user/user.model";
import {ProgressBarMode} from "@angular/material/progress-bar";

export class RefactoringService {
  compiledExercise !: Exercise;
  user!: User;
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
  methodList: string[] = [];
  exerciseConfiguration!: RefactoringGameExerciseConfiguration;

  originalProductionCode = ""
  originalTestCode = ""

  // MESSAGES
  exerciseIsCompiledSuccessfully: boolean = false;
  smellNumberWarning: boolean = false;
  refactoringWarning: boolean = false;
  smellDescriptions: SmellDescription[] = [];
  originalCoverage: number = -1;
  refactoredCoverage: number = -1;

  constructor(
    private codeService: CodeeditorService,
    private exerciseService: ExerciseService,
    private leaderboardService: LeaderboardService,
    private snackBar: MatSnackBar,
    private userService: UserService
  ) {}

  initCodeFromCloud(exerciseName: string): void {
    // INIT CODE FROM CLOUD
    this.exerciseService.getMainClass(exerciseName).subscribe( data=> {
      this.userCode = data;
      this.originalProductionCode = data;
    });
    this.exerciseService.getTestClass(exerciseName).subscribe( data => {
      this.testingCode = data
      this.originalTestCode = data
    });
    this.exerciseService.getRefactoringGameConfigFile(exerciseName).subscribe(data=>{
      this.exerciseConfiguration = data;

      // Setup config files
      this.exerciseConfiguration.refactoring_game_configuration.refactoring_limit = data.refactoring_game_configuration.refactoring_limit;
      this.exerciseConfiguration.refactoring_game_configuration.smells_allowed = data.refactoring_game_configuration.smells_allowed;
    });
  }

  async initSmellDescriptions() {
    // @ts-ignore
    await import('./smell_description.json').then((data) => {
      this.smellDescriptions = data.smells;
    });
  }

  restoreCode(gameType: string, exerciseName: string): void {
    const savedCode = localStorage.getItem(`${gameType}-${exerciseName}`);
    if (savedCode) {
      const { productionCode, testCode } = JSON.parse(savedCode);
      this.userCode = productionCode;
      this.testingCode = testCode;
      this.originalProductionCode = productionCode;
    } else {
      this.exerciseService.getMainClass(exerciseName).subscribe(data => {
        this.userCode = data;
        this.originalProductionCode = data;
      });
      this.exerciseService.getTestClass(exerciseName).subscribe(data => {
        this.testingCode = data;
        this.originalTestCode = data;
      });
    }
  }

  saveCode(gameType: string, exerciseName: string, testing: any) {
    const exerciseCode = {
      productionCode: this.originalProductionCode,
      testCode: testing.injectedCode
    };
    localStorage.setItem(`${gameType}-${exerciseName}`, JSON.stringify(exerciseCode));
  }

  startLoading(): void {
    this.progressBarMode = 'query';
  }

  stopLoading(): void {
    this.progressBarMode = 'determinate';
  }

  resetData(): void {
    this.shellCode = '';
    this.smells = '';
    this.refactoringResult = '';
    this.smellList = [];
    this.smellResult = [];
    this.methodList = [];
    this.smellNumber = 0;
    this.exerciseIsCompiledSuccessfully = false;
    this.smellNumberWarning = false;
    this.refactoringWarning = false;
    this.originalCoverage = -1;
    this.refactoredCoverage = -1;
  }

  showPopUp(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
    });
  }

  compileExercise(exerciseName: string, testing: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.resetData();
      this.startLoading();

      console.log("testingCode: ", testing.injectedCode);

      // @ts-ignore
      const exercise = new Exercise(exerciseName, this.originalProductionCode, this.originalTestCode, testing.injectedCode);
      this.compiledExercise = exercise;

      this.codeService.compile(exercise, this.exerciseConfiguration).subscribe(
        (data) => {
          this.elaborateCompilerAnswer(data);
          this.stopLoading();
          resolve(true);
        },
        (error) => {
          this.showPopUp('Cloud server has a problem');
          this.stopLoading();
          resolve(false);
        }
      );
    });
  }

  publishSolution(exerciseName: string): void {
    this.startLoading()
    if(this.exerciseIsCompiledSuccessfully){
      this.leaderboardService.saveSolution(this.compiledExercise,
        this.exerciseConfiguration,
        Math.abs(this.smellNumber - this.exerciseConfiguration.refactoring_game_configuration.smells_allowed),
        Boolean(this.refactoringResult),
        this.originalCoverage,
        this.refactoredCoverage,
        this.smells).subscribe(
          result => {
            this.showPopUp("Solution saved");
            this.stopLoading();
            this.userService.getCurrentUser().subscribe((user: User | any) => {
              this.user = user;
            });
            this.userService.increaseUserExp();

            this.leaderboardService.updateScore(this.user.userId, "refactoring", 1).subscribe(
              (data) => {
                console.log("Updated exercise score: ", data);

                this.leaderboardService.updateBestRefactoringScore(this.user.userId, exerciseName, this.smellNumber).subscribe(
                  (data) => {
                    console.log("Updated score: ", data);
                  }
                );
              }
            );

            this.exerciseService.logEvent(this.user.userName, 'Completed ' + exerciseName + ' in refactoring game mode').subscribe({
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

  elaborateCompilerAnswer(data: any): void {
    this.shellCode = data.testResult;
    this.smells = data.smellResult;
    this.refactoringResult = data.similarityResponse;
    this.exerciseIsCompiledSuccessfully = data.success;
    this.originalCoverage = data.originalCoverage;
    this.refactoredCoverage = data.refactoredCoverage;
    this.stopLoading();

    if (this.exerciseIsCompiledSuccessfully) {
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

  checkConfiguration(): void {
    if (this.refactoringResult.toString() === 'false')
      this.refactoringWarning = true;
    if (this.exerciseConfiguration.refactoring_game_configuration.smells_allowed < this.smellNumber)
      this.smellNumberWarning = true;
  }
}
