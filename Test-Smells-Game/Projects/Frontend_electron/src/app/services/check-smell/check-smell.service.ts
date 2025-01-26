import {Injectable, NgZone} from '@angular/core';
import {firstValueFrom, Observable} from "rxjs";
import {ToolConfig} from "../../model/toolConfig/tool.config.model";
import {User} from "../../model/user/user.model";
import {Question} from "../../model/question/question.model";
import {CheckGameExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";
import {ExerciseService} from "../exercise/exercise.service";
import {ActivatedRoute} from "@angular/router";
import {UserService} from "../user/user.service";
import {SuccessAlertComponent} from "../../components/alert/success-alert/success-alert.component";
import {AchievementAlertComponent} from "../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../components/alert/fail-alert/fail-alert.component";
import {LeaderboardService} from "../leaderboard/leaderboard.service";
import {ElectronService} from "ngx-electron";

export class CheckSmellService {
  config!: ToolConfig;
  user!: User;
  actualQuestionNumber: number = 0;

  questions!: Question[];
  exerciseConfiguration!: CheckGameExerciseConfiguration;

  exerciseCompleted: boolean = false;
  score: number = 0;
  assignmentScore: number = 0;

  constructor(
    private exerciseService: ExerciseService,
    private userService: UserService,
    private _electronService: ElectronService,
    private zone: NgZone
  ) { }

  async initQuestionsFromCloud(exerciseName: string): Promise<string | undefined> {
    try {

      const data = await firstValueFrom(this.exerciseService.getCheckGameConfigFile(exerciseName));
      this.exerciseConfiguration = data;
      this.questions = data.checkGameConfiguration.questions;

      this.config = await firstValueFrom(this.exerciseService.getToolConfig());
      return undefined;
    } catch (error) {
      // @ts-ignore
      return error.error.message;
    }
  }

  async initQuestionsFromLocal(exerciseName: string): Promise<string | undefined> {
    try {
      this.exerciseService.initCheckSmellExerciseConfigFromLocal(exerciseName);
      this._electronService.ipcRenderer.on('receiveCheckGameConfigFromLocal', (event, data: CheckGameExerciseConfiguration)=>{
        this.zone.run(()=>{
          this.exerciseConfiguration = CheckGameExerciseConfiguration.fromJson(data);
          this.questions = this.exerciseConfiguration.checkGameConfiguration.questions;

          console.log(this.questions);
        });
      });

      this.config = await firstValueFrom(this.exerciseService.getToolConfig());
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

  calculateScore(): [number, number, number] {
    let correctAnswers = 0;
    let wrongAnswers = 0;
    let missedAnswers = 0;

    this.assignmentScore = 0;
    let score = 0;

    console.log("Questions: ", this.questions);
    this.questions.forEach(question => {
      let currentCorrectAnswers = 0;
      let givenAnswersScore = 0;

      question.answers.forEach(ans => {
        ans.correct? currentCorrectAnswers += 1 : 0;
        if (ans.isChecked) {
          if (ans.correct) {
            givenAnswersScore += 1;
            correctAnswers += 1;
          } else {
            givenAnswersScore -= 0.5;
            wrongAnswers += 1;
          }
        } else if (!ans.isChecked && ans.correct) {
          missedAnswers += 1;
        }
      });
      this.assignmentScore += currentCorrectAnswers;
      score += givenAnswersScore;

      console.log("assignmentScore: ", this.assignmentScore);
      console.log("score: ", score)
    });

    if (score < 0)
      score = 0;

    this.score = score;
    return [correctAnswers, wrongAnswers, missedAnswers];
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

  generateCheckSmellReport(): string {
      const studentScore = Math.round((this.score * 100) / this.assignmentScore);
      const assignmentScore = this.assignmentScore;
      const questions = this.questions;

      let content = `Student score: ${studentScore}\n`;
      content += `Assignment total score: ${assignmentScore}\n\n`;

      questions.forEach(question => {
        let questionPoints: number = 0;
        let givenPoints: number = 0;
        let lostPoints: number = 0;

        content += `Question ${questions.indexOf(question)}:\n`;

        content += "\tGiven answers: [";
        question.answers.forEach(ans => {
          if (ans.isChecked) {
            content += `${ans.answerText}, `;
            if (ans.correct)
              givenPoints += 1;
            else
              lostPoints += 0.5;
          }
        });
        content = content.substring(0, content.length-2);
        content += "]\n";

        content += "\tCorrect answers: [";
        question.answers.forEach(ans => {
          ans.correct? content += `${ans.answerText}, ` : "";
          ans.correct? questionPoints++ : 0;
        });
        content = content.substring(0, content.length-2);
        content += "]\n";

        content += `\tQuestion points: ${questionPoints}\n`;
        content += `\tPoint from correct answers: ${givenPoints}\n`;
        content += `\tPoints lost from incorrect answers: ${lostPoints}\n`
        content += `\tTotal points given: ${Math.max(0, givenPoints - lostPoints)}\n\n`;
      });

      return content;
    }

}
