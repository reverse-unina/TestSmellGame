import { Injectable } from '@angular/core';
import {firstValueFrom, Observable} from "rxjs";
import {levelConfig} from "../../model/levelConfiguration/level.configuration.model";
import {User} from "../../model/user/user.model";
import {Question} from "../../model/question/question.model";
import {CheckGameExerciseConfig} from "../../model/exercise/ExerciseConfiguration.model";
import {ExerciseService} from "../exercise/exercise.service";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../user/user.service";
import {SuccessAlertComponent} from "../../components/alert/success-alert/success-alert.component";
import {AchievementAlertComponent} from "../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../components/alert/fail-alert/fail-alert.component";
import {LeaderboardService} from "../leaderboard/leaderboard.service";

export class CheckSmellService {
  config!: levelConfig;
  user!: User;
  exerciseRetrievalType!: number;
  actualQuestionNumber: number = 0;

  questions!: Question[];
  exerciseConfiguration!: CheckGameExerciseConfig;

  exerciseCompleted: boolean = false;
  score: number = 0;
  assignmentScore: number = 0;

  constructor(
    private exerciseService: ExerciseService,
    private userService: UserService,
    private leaderboardService: LeaderboardService
  ) { }

  async initQuestions(exerciseName: string): Promise<void> {
    this.exerciseRetrievalType = Number(localStorage.getItem("exerciseRetrieval"));

    // Setup question
    this.exerciseService.getCheckGameConfigFile(exerciseName).subscribe(data => {
      this.exerciseConfiguration = data;
      this.questions = data.check_game_configuration.questions;
    });

    this.config = await firstValueFrom(this.exerciseService.getLevelConfig());
  }

  goBackward() {
    if (this.actualQuestionNumber > 0) {
      this.actualQuestionNumber -= 1;
    }
  }

  goForward() {
    if (this.actualQuestionNumber < this.questions.length - 1) {
      this.actualQuestionNumber += 1;
    }
  }

  async showResults(exerciseName: string): Promise<void> {
    this.exerciseCompleted = true;

    if (this.isExercisePassed()) {
      await this.userService.increaseUserExp();

      this.userService.getCurrentUser().subscribe((user: User | any) => {
        this.user = user;
      });
      this.exerciseService.logEvent(this.user.userName, 'Completed ' + exerciseName + ' in check game mode').subscribe({
        next: response => console.log('Log event response:', response),
        error: error => console.error('Error submitting log:', error)
      });

      this.leaderboardService.updateScore(this.user.userId, "check-smell", 1).subscribe(
        data => {console.log("Rank: ", data)}
      )
    }
  }

  calculateScore() {
    this.assignmentScore = 0;
    let score = 0;
    this.questions.forEach(question => {
      let currentCorrectAnswers = 0;
      let givenAnswersScore = 0;
      question.answers.forEach(ans => {
        ans.isCorrect? currentCorrectAnswers++ : 0;

        if (ans.isChecked) {
          if (ans.isCorrect)
            givenAnswersScore++;
          else
            givenAnswersScore -= 0.5;
        }
      });
      this.assignmentScore += currentCorrectAnswers;
      score += givenAnswersScore;
    });

    if (score < 0)
      score = 0;

    this.score = score;
  }

  renderResultsButton():boolean {
    if (this.actualQuestionNumber !== this.questions.length - 1)
      return false;

    let allQuestionsAnswered = true;
    this.questions.forEach(question => {
      allQuestionsAnswered &&= question.answers.some(answer => answer.isChecked);
    })

    return allQuestionsAnswered;
  }

  isExercisePassed(): boolean {
    return Math.round((this.score * 100) / this.assignmentScore) >= this.config.answerPercentage;
  }

}
