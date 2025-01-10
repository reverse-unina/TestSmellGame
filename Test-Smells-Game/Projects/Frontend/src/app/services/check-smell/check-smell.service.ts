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

  async initQuestions(exerciseName: string): Promise<string | undefined> {
    try {
      this.exerciseRetrievalType = Number(localStorage.getItem("exerciseRetrieval"));

      const data = await firstValueFrom(this.exerciseService.getCheckGameConfigFile(exerciseName));
      this.exerciseConfiguration = data;
      this.questions = data.checkGameConfiguration.questions;

      this.config = await firstValueFrom(this.exerciseService.getLevelConfig());
      return undefined;
    } catch (error) {
      // @ts-ignore
      return error.error.message;
    }
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

  async logResult(exerciseName: string, gameType: string): Promise<void> {
    this.exerciseCompleted = true;

    if (this.isExercisePassed()) {
      this.userService.getCurrentUser().subscribe((user: User | any) => {
        this.user = user;
      });
      this.exerciseService.logEvent(this.user.userName, 'Completed ' + exerciseName + ' in ' + gameType + ' mode' ).subscribe({
        next: response => console.log('Log event response:', response),
        error: error => console.error('Error submitting log:', error)
      });
    }
  }

  calculateScore() {
    this.assignmentScore = 0;
    let score = 0;
    this.questions.forEach(question => {
      let currentCorrectAnswers = 0;
      let givenAnswersScore = 0;

      question.answers.forEach(ans => {
        ans.correct? currentCorrectAnswers++ : 0;
        if (ans.isChecked) {
          if (ans.correct)
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
