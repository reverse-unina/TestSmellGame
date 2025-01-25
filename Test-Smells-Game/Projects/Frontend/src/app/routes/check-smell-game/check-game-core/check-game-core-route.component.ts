import {Component, HostListener, OnInit, ViewChild, Input, Output, EventEmitter, SimpleChanges} from '@angular/core';
import {CheckSmellService} from "../../../services/check-smell/check-smell.service";
import {ActivatedRoute} from "@angular/router";
import {SuccessAlertComponent} from "../../../components/alert/success-alert/success-alert.component";
import {AchievementAlertComponent} from "../../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../../components/alert/fail-alert/fail-alert.component";
import {Question} from "../../../model/question/question.model";
import {ExerciseService} from "../../../services/exercise/exercise.service";
import {UserService} from "../../../services/user/user.service";
import {LeaderboardService} from "../../../services/leaderboard/leaderboard.service";

@Component({
  selector: 'app-check-game-core',
  templateUrl: './check-game-core-route.component.html',
  styleUrls: ['./check-game-core-route.component.css']
})
export class CheckGameCoreRouteComponent implements OnInit {
  exerciseName!: string;

  @ViewChild('achievementAlert') achievementAlert!: AchievementAlertComponent;
  @ViewChild('failAlert') failAlert!: FailAlertComponent;
  @ViewChild('successAlert') successAlert!: SuccessAlertComponent;


  @Input() exerciseNameTest!: string;
  @Input() isMultiLevelGame: boolean = false;
  @Output() exerciseCompleted = new EventEmitter<any>();
  @Input() savedAnswers: { [questionCode: string]: string } = {};

  
  isCompleteDisabled: boolean = false;


  protected checkSmellService!: CheckSmellService;

  constructor(
    private exerciseService: ExerciseService,
    private userService: UserService,
    private route: ActivatedRoute,
    private leaderboardService: LeaderboardService
  ) {
    //this.exerciseName = decodeURIComponent(this.route.snapshot.params['exercise']);
    this.checkSmellService = new CheckSmellService(
      this.exerciseService,
      this.userService,
      this.leaderboardService
    )
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      
      if (this.isMultiLevelGame){
        this.exerciseName = this.exerciseNameTest;
        this.checkSmellService.initQuestions(this.exerciseName, true).then(() => {

          if (this.savedAnswers) {
            this.checkSmellService.questions.forEach((question) => {
              if (this.savedAnswers[question.questionCode]) {
                const selectedAnswer = this.savedAnswers[question.questionCode];
                const answer = question.answers.find(
                  (ans: any) => ans.answerText === selectedAnswer
                );
  
                if (answer)
                  answer.isChecked = true;
              }
            });
          }    

        });
      } 
      else {
        this.exerciseName = params['exercise'];
        this.checkSmellService.initQuestions(this.exerciseName);
      }
    });
  }

  async submitExercise(): Promise<void> {

    if(this.isMultiLevelGame){
      this.isCompleteDisabled = true;
      
      const data = {
        exerciseName: this.exerciseName,
        questions: this.checkSmellService.questions.map((q: any) => {
          const correctAnswer = q.answers.find((ans: any) => ans.correct)?.answerText || null;
          const selectedAnswer = q.answers.find((ans: any) => ans.isChecked)?.answerText || null;

          return {
            questionCode: q.questionCode,
            answers: q.answers.map((ans: any) => ({
              answerText: ans.answerText,
              correct: ans.correct,
              isChecked: ans.isChecked || false,
            })),
            correctAnswer,
            selectedAnswer,
          };
        }),
      };

      this.exerciseCompleted.emit(data);

      } else {
        this.isCompleteDisabled = false; 
        this.checkSmellService.calculateScore();
        this.checkSmellService.logResult(this.exerciseName, "check game").then(
        () => {

          if (this.checkSmellService.isExercisePassed()) {
            this.successAlert.show();

            this.leaderboardService.getScore(this.userService.user.value.userName).subscribe(
              result => {
                const currentScore = result;
                this.leaderboardService.updateBestCheckSmellScore(this.userService.user.value.userName, this.exerciseName, 1).subscribe(
                  (updatedScore) => {
                    console.log("Current score: ", currentScore);
                    console.log("Updated score: ", updatedScore);
                    if (updatedScore.checkSmellScore !== currentScore.checkSmellScore) {
                      this.userService.increaseUserExp(1).then(
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
          } else {
            this.failAlert.show();
          }
        }
      );
    }
  }

  protected readonly Math = Math;


  ngOnChanges(changes: SimpleChanges): void {
      if (changes['exerciseNameTest'] && !changes['exerciseNameTest'].firstChange) {
        console.log('Cambio rilevato per exerciseNameTest:', changes['exerciseNameTest']);
        this.reloadExercise();
      }
  }


  reloadExercise(){
    this.route.params.subscribe(params => {
      
      if (this.isMultiLevelGame){
        this.exerciseName = this.exerciseNameTest;
        this.isCompleteDisabled = false;
        this.checkSmellService.initQuestions(this.exerciseName, true);
      } else {
        this.exerciseName = params['exercise'];
        this.checkSmellService.initQuestions(this.exerciseName);
      }
    });
  }


  completeExercise(): void {
    const data = {
      score: this.checkSmellService.score,
      assignmentScore: this.checkSmellService.assignmentScore,
      questions: this.checkSmellService.questions,
      answers: this.checkSmellService.questions.map((q: any) => ({
        questionCode: q.questionCode,
        answers: q.answers,
        correctAnswer: q.answers.find((ans: any) => ans.isCorrect)?.answerText,
        selectedAnswer: q.selectedAnswer
      }))
    };
  
    //console.log('Dati dell\'esercizio di check-game completato:', data);
    this.exerciseCompleted.emit(data);
  }
  
}
