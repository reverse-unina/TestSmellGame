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
import {firstValueFrom} from "rxjs";

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

  async initCodeFromCloud(exerciseName: string): Promise<string | undefined> {
    try {
      const mainClassData = await firstValueFrom(this.exerciseService.getMainClass(exerciseName));
      this.userCode = mainClassData;
      this.originalProductionCode = mainClassData;

      const testClassData = await firstValueFrom(this.exerciseService.getTestClass(exerciseName));
      this.testingCode = testClassData;
      this.originalTestCode = testClassData;

      const refactoringConfigData = await firstValueFrom(this.exerciseService.getRefactoringGameConfigFile(exerciseName));
      this.exerciseConfiguration = refactoringConfigData;

      // Setup config files
      this.exerciseConfiguration.refactoringGameConfiguration.refactoringLimit = refactoringConfigData.refactoringGameConfiguration.refactoringLimit;
      this.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed = refactoringConfigData.refactoringGameConfiguration.smellsAllowed;

      return undefined;
    } catch (error) {
      // @ts-ignore
      return error.error.message;
    }
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

  compileExercise(gameMode: string, testing: any): Promise<boolean> {
    return new Promise((resolve, reject) => {
      this.resetData();
      this.startLoading();

      this.exerciseService.logEvent(gameMode, this.userService.user.value.userName, "start compiling refactoring exercise " + this.exerciseConfiguration.className).subscribe(
        next => {
          console.log(JSON.stringify(next));
        }
      );

      // @ts-ignore
      const exercise = new Exercise(this.exerciseConfiguration.className, this.originalProductionCode, this.originalTestCode, testing.injectedCode);
      this.compiledExercise = exercise;

      //console.log("Exercise: ", exercise);

      this.codeService.compile(exercise, this.exerciseConfiguration).subscribe(
        (data) => {
          //console.log("Compiled");
          this.elaborateCompilerAnswer(data);
          this.stopLoading();
          this.exerciseService.logEvent(gameMode, this.userService.user.value.userName,
            "compiled refactoring exercise " + this.exerciseConfiguration.className + " with result: \n" + JSON.stringify(data, null, 2)
          ).subscribe(
            next => {
              console.log(JSON.stringify(next));
            });

          const productionCode = this.userCode;
          const testCode = testing.injectedCode;
          const shellCode = this.shellCode;
          const results = this.generateResultsContent();

          this.exerciseService.getToolConfig().subscribe(
            next => {
              if (next.logTries) {
                this.exerciseService.submitRefactoringExercise(gameMode, this.userService.user.value.userName, this.exerciseConfiguration.exerciseId, productionCode, testCode, shellCode, results).subscribe(
                  result => {
                    console.log(JSON.stringify(result));
                  }
                );
              }
            }
          );

          resolve(true);
        },
        (error) => {
          this.showPopUp('Cloud server has a problem');
          this.stopLoading();
          this.exerciseService.logEvent(gameMode, this.userService.user.value.userName, "compiling refactoring exercise " + this.exerciseConfiguration.className + " encountered an error: " + error);
          resolve(false);
        }
      );
    });
  }

  generateResultsContent(): string {
    const score = this.isExercisePassed() ? Math.abs(this.smellNumber - this.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed) : -1;
    let content = `Score: ${score}\n\n`;

    if (this.refactoringResult !== undefined) {
      content += `Refactoring result: ${this.refactoringResult}\n`;
      content += `Original coverage: ${this.originalCoverage}\n`;
      content += `Refactored coverage: ${this.refactoredCoverage}\n\n`;
    }

    if (this.smellNumberWarning) {
      content += `Smells allowed: ${this.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed}\n`;
      content += `Your refactored code has more smells (${this.smellNumber}) than the minimum accepted\n\n`;
    }

    content += "Smells:\n";
    for (let i = 0; i < this.smellList.length; i++) {
      content += `${this.smellList[i]}: ${this.methodList[i].length}\n`;
      content += `${this.smellDescriptions[this.getSmellNumber(this.smellList[i])].smellDescription}\n`;
      for (let j = 0; j < this.methodList[i].length; j++) {
        content += `${this.methodList[i][j]}\n`;
      }
      content += "\n";
    }

    return content;
  }

  isExercisePassed(): boolean {
    return this.exerciseIsCompiledSuccessfully &&
            this.refactoredCoverage >= (this.originalCoverage - this.exerciseConfiguration.refactoringGameConfiguration.refactoringLimit) &&
            this.smellNumber <= this.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed;
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
    if (this.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed < this.smellNumber)
      this.smellNumberWarning = true;
  }

  getSmellNumber(smell: string) {
    switch (smell) {
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
