import { Component, OnInit, ViewChild } from '@angular/core';
import { ExerciseService } from 'src/app/services/exercise/exercise.service';
import { ChangeDetectorRef } from '@angular/core';
import { TestService } from 'src/app/services/test/test.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CheckSmellService } from 'src/app/services/check-smell/check-smell.service';
import { RefactoringGameCoreRouteComponent } from "../refactoring-game/refactoring-game-core/refactoring-game-core-route.component";

@Component({
  selector: 'app-test-game',
  templateUrl: './test-game.component.html',
  styleUrls: ['./test-game.component.css']
})
export class TestGameComponent implements OnInit{

  @ViewChild('refactoringGameCore', { static: false }) refactoringGameCore!: RefactoringGameCoreRouteComponent;
  protected checkSmellService!: CheckSmellService;
  protected readonly Math = Math;


  exercises: any[] = [];
  currentExerciseIndex = 0;
  currentLevel: number = 1;
  completedCurrentExercise: boolean = false; 
  selectedAnswer: string | null = null;
  isMultiLevelGame: boolean = true;
  exerciseStates: any[] = [];


  correctAnswersList: { exerciseId: string, questionText: string; selectedAnswer: string; correctAnswer: string, level: number }[] = [];
  wrongAnswersList: { exerciseId: string, questionText: string; selectedAnswer: string; correctAnswer: string, level:number }[] = [];

  /*completedExercisesList: Array<{
    exerciseId: string;
    type: 'check-game' | 'refactoring-game';
    level: number;
    data: {
        refactoringResult?: boolean; 
        originalCoverage?: number;  
        refactoredCoverage?: number;
        smellsAllowed?: number;
        smellNumber?: number;
    };
  }> = [];*/


  //NUOVO
  completedCheckGameExercises: any[] = [];
  completedRefactoringExercises: any[] = [];


  refactoringResult: boolean = false; 
  originalCoverage: number = 0;
  refactoredCoverage: number = 0;
  smellsAllowed: number = 0;
  smellNumber: number = 0;



  startTime!: Date; 
  timeLeft: number = 600; // Tempo rimanente
  timer!: any;

  selectedAnswersByExercise: { [exerciseName: string]: { [questionCode: string]: string } } = {};


  constructor(
    private exerciseService: ExerciseService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private testService: TestService,
    private translate: TranslateService,
    private snackBar: MatSnackBar
  ){}


  ngOnInit(): void {
    this.startTime = new Date();
    const savedState = localStorage.getItem('multiGameTestState');
    if (savedState) {
      const testState = JSON.parse(savedState);

      const message = this.translate.currentLang === 'it' ? 'Hai un test in corso. Vuoi riprendere da dove hai lasciato?' : 'You have a test in progress. Do you want to resume where you left off?'
      const resume = confirm(message);

      if (resume) {
        this.loadSavedState(testState);
      } else {
        this.startNewTest();
      }
    } else {
      this.startNewTest();
    }
    this.startTimer();
 }

 

  loadSavedState(testState: any): void {
    this.currentExerciseIndex = testState.currentExerciseIndex || 0;
    this.currentLevel = testState.currentLevel || 1;
    this.selectedAnswer = testState.selectedAnswer || null;
    this.correctAnswersList = testState.correctAnswersList || [];
    this.wrongAnswersList = testState.wrongAnswersList || [];
    this.completedCheckGameExercises = testState.completedCheckExercises || [];
    this.completedRefactoringExercises = testState.completedRefactoringExercises || [];
    this.exercises = testState.exercises || [];
    this.timeLeft = testState.timeLeft || 600;

    //console.log('Test ripreso con stato:', testState);
  }

  
  startNewTest(): void {
    localStorage.removeItem('multiGameTestState');
    this.currentLevel = 1;
    this.selectedAnswer = null;
    this.correctAnswersList = [];
    this.wrongAnswersList = [];
    this.completedCheckGameExercises = [];
    this.completedRefactoringExercises = [];
    this.exercises = [];
    this.loadExercisesByLevel(this.currentLevel);
  }
  


  loadExercisesByLevel(level: number): Promise<void> {
    return new Promise((resolve) => {
      this.exerciseService.getExercisesByLevel(level).subscribe({
        next: (data) => {
          if (data.length === 0) {
            console.error(`Nessun esercizio disponibile per il livello ${level}.`);
            this.submitTest();
            resolve();
            return;
          }

          this.exercises = data;
          this.filterExercisesByTypeRandom(2, 2);
          this.currentExerciseIndex = 0;

          resolve();
        },
        error: (err) => {
          console.error('Errore nel caricamento degli esercizi:', err);
          alert('Errore durante il caricamento degli esercizi.');
          resolve();
        }
      });
    });
  }



  normalizeExerciseId(exerciseId: string): string {
    return exerciseId.replace(/_\d+$/, "");
  }
  

  filterExercisesByTypeRandom(checkCount: number, refactoringCount: number): void {
    const shuffledExercises = this.exercises.sort(() => Math.random() - 0.5);

    const checkGameExercises = shuffledExercises.filter((exercise) => exercise.checkGameConfiguration != undefined && exercise.checkGameConfiguration != null);
    const refactoringGameExercises = shuffledExercises.filter((exercise) => exercise.refactoringGameConfiguration !== undefined && exercise.refactoringGameConfiguration !== null);

    console.log("check-game", checkGameExercises);
    console.log("refactor", refactoringGameExercises);

    const selectedCheckGameExercises = checkGameExercises.slice(0, checkCount).map((exercise) => ({
      type: 'check-game',
      data: exercise
    }));

    const selectedRefactoringExercises = refactoringGameExercises.slice(0, refactoringCount).map((exercise) => ({
      type: 'refactoring-game',
      data: exercise
    }));

    
    this.exercises = [...selectedCheckGameExercises, ...selectedRefactoringExercises];
    this.exercises = this.exercises.sort(() => Math.random() - 0.5);
    this.currentExerciseIndex = 0;
  }



  
  
  nextExercise(): void {
    this.saveCurrentState();

    if (this.currentExerciseIndex < this.exercises.length - 1){
      this.currentExerciseIndex++;

      this.completedCurrentExercise = false;
      this.selectedAnswer = null;

      const currentExercise = this.exercises[this.currentExerciseIndex];

      if (!currentExercise?.type) {
        console.error('Tipo di esercizio non valido.');
        return;
      }

      this.cdr.detectChanges();

    } else {
        
        if (this.currentLevel >= 2) {
          this.showNotification('Hai completato tutti i livelli del test!');
          this.submitTest();
        } else {
            this.currentLevel++;
            this.loadExercisesByLevel(this.currentLevel);
        }
    }
  }


  

  previousExercise(): void {
    if (this.currentExerciseIndex > 0) {
      this.saveCurrentState();
      this.currentExerciseIndex--;

      const previousState = this.exerciseStates[this.currentExerciseIndex];
      if (previousState ) {
        this.selectedAnswer = previousState.selectedAnswer || null;
        this.completedCurrentExercise = previousState.completeCurrentExercise || false;
      }    
    } else {
      console.log('Nessun esercizio precedente.');
    }
  }
  


  submitTest(): void {
    this.showNotification('Test consegnato! Grazie per aver completato il Multi-Level Game.');

    const endTime = new Date();
    const elapsedTime = Math.floor((endTime.getTime() - this.startTime.getTime()) / 1000);
    const remainingTime = 600 - elapsedTime;

    const formattedTime = remainingTime > 0 ? remainingTime : 0;

    this.testService.setData({
      exerciseName: this.exercises[this.currentExerciseIndex].data.exerciseId || '',
      correctAnswersList: this.correctAnswersList,
      wrongAnswersList: this.wrongAnswersList,

      completedCheckExercisesList: this.completedCheckGameExercises,
      completedRefactoringExercisesList: this.completedRefactoringExercises,

      completionTime: this.formatTime(elapsedTime),
      type: this.exercises[this.currentExerciseIndex].type
    });

    localStorage.removeItem('multiGameTestState');
    console.log('Stato del test cancellato.');

    this.router.navigate(['/test-summary']);
  }
  


  saveCurrentState(): void {
    const currentExercise = this.exercises[this.currentExerciseIndex];

    const selectedAnswers = this.completedCheckGameExercises.map((exercise: any) => {
      return {
        exerciseName: exercise.exerciseName,
        selectedAnswers: exercise.questions.map((question: any) => ({
          questionCode: question.questionCode,
          selectedAnswer: question.selectedAnswer,
        })) || [],
      };
    }) || [];
          
    const testState = {
      currentExerciseIndex: this.currentExerciseIndex,
      currentLevel: this.currentLevel,
      selectedAnswers: selectedAnswers,
      correctAnswersList: this.correctAnswersList,
      wrongAnswersList: this.wrongAnswersList,
      completedCheckGameExercisesList: this.completedCheckGameExercises,
      completedRefactoringExercisesList: this.completedRefactoringExercises,
      exercises : this.exercises,
      timeLeft: this.timeLeft,
      startTime: this.startTime.toISOString()
    };
    
    localStorage.setItem('multiGameTestState', JSON.stringify(testState));
    console.log('Stato del test salvato:', testState);
  }



    
  startTimer(): void {
    this.timer = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        clearInterval(this.timer);
        this.timeExpired();
      }
    }, 1000);
  }


  timeExpired(): void {
    alert('Tempo scaduto! Il test verrà inviato automaticamente.');
    this.submitTest();
  }

  formatTime(seconds: number): string {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}`;
  }

  ngOnDestroy(): void {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }



  showNotification(message: string, action: string = 'Close') {
    this.snackBar.open(message, action, {
      duration: 3000, // Tempo in millisecondi
      horizontalPosition: 'center',
      verticalPosition: 'bottom'
    });
  } 
  


  handleCompletedCheckGame(data: any): void {
    
    const existingIndex = this.completedCheckGameExercises.findIndex(
      (exercise) => exercise.exerciseName === data.exerciseName
    );
  
    if (existingIndex !== -1) {
      this.completedCheckGameExercises[existingIndex] = data;
    } else { 
      this.completedCheckGameExercises.push(data);

      this.selectedAnswersByExercise[data.exerciseName] = data.questions.reduce(
        (acc: { [questionCode: string]: string }, question: any) => {
          if (question.selectedAnswer) {
            acc[question.questionCode] = question.selectedAnswer;
          }
          return acc;
        },
        {}
      );

      data.questions.forEach((question: any) => {
        const { questionCode, correctAnswer, selectedAnswer } = question;

        if (correctAnswer && selectedAnswer) { 
          if (correctAnswer === selectedAnswer) {
            this.correctAnswersList.push({
              exerciseId: this.normalizeExerciseId(data.exerciseName),
              questionText: questionCode,
              selectedAnswer: selectedAnswer,
              correctAnswer: correctAnswer,
              level: this.currentLevel,
            });
          } else {
            this.wrongAnswersList.push({
              exerciseId: this.normalizeExerciseId(data.exerciseName),
              questionText: questionCode,
              selectedAnswer: selectedAnswer,
              correctAnswer: correctAnswer,
              level: this.currentLevel,
            });
          }
      } else {
        console.error(
          `Errore: Domanda o risposta mancante per ${questionCode}.`
        );
      }
    });
  }  

    this.saveCurrentState();
  }

  
  handleCompletedRefactoringGame(data: any): void {
    const existingIndex = this.completedCheckGameExercises.findIndex(
      (exercise) => exercise.exerciseName === data.exerciseName
    );
  
    if (existingIndex !== -1) {
      this.completedRefactoringExercises[existingIndex] = data;
    } else { 
      this.completedRefactoringExercises.push(data);
    
      this.saveCurrentState();
    }
  }
  


  getSelectedAnswersForCurrentExercise(): { [questionCode: string]: string } {
    const currentExercise = this.exercises[this.currentExerciseIndex]?.data?.exerciseId;
  
    const exerciseAnswers = this.completedCheckGameExercises.find(
      (exercise: any) => exercise.exerciseName === currentExercise
    );
  
    if (!exerciseAnswers) {
      return {};
    }
      return exerciseAnswers.questions.reduce((acc: any, question: any) => {
      acc[question.questionCode] = question.selectedAnswer;
      return acc;
    }, {});
  }
  

}
