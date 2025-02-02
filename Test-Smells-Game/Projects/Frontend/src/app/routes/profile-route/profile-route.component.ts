import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../model/user/user.model';
import { ToolConfig } from "src/app/model/toolConfig/tool.config.model"
import { ExerciseService } from '../../services/exercise/exercise.service'
import {firstValueFrom} from "rxjs";
import {MissionService} from "../../services/missions/mission.service";
import {MissionConfiguration, MissionStatus} from "../../model/missions/mission.model";
import {environment} from "../../../environments/environment.prod";
import {LeaderboardService} from "../../services/leaderboard/leaderboard.service";
import {PodiumRanking, Score, UserRanking} from "../../model/rank/score";
import Chart from 'chart.js/auto';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import {UserSubmitHistory} from "../../model/userSubmitHistory/user-submit-history";



@Component({
  selector: 'app-profile-route',
  templateUrl: './profile-route.component.html',
  styleUrls: ['./profile-route.component.css']
})
export class ProfileRouteComponent implements OnInit {

  config!: ToolConfig;
  user!: User;
  userLevel!: string;
  missionConfigurations!: MissionConfiguration[];
  userMissionsStatus!: MissionStatus[];
  userScore!: Score;
  userRank!: UserRanking;
  topGameModeUsers!: PodiumRanking;
  topRefactoringUsers!: PodiumRanking;
  serverError: string | undefined;
  userSubmitHistory!: UserSubmitHistory[];

  private chartInstanceCheckGame: Chart | null = null;
  private chartInstanceRefactoringGame: Chart | null = null;

  constructor(
    private userService: UserService,
    private exerciseService: ExerciseService,
    private missionService: MissionService,
    private leaderboardService: LeaderboardService
  ) { }

  async ngOnInit(): Promise<void> {
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
    });

    this.config = await firstValueFrom(this.exerciseService.getToolConfig());
    this.setUserLevel();
    //console.log('toolConfig:', this.config);

    try {
      this.missionConfigurations = await firstValueFrom(this.missionService.getMissions());
      this.userMissionsStatus = await firstValueFrom(this.userService.getUserMissionsStatus());
      this.serverError = undefined;
    } catch (error) {
      //console.log(error);
      // @ts-ignore
      this.serverError = error.error.message || 'An unexpected error occurred';
    }

    this.userScore = await firstValueFrom(this.leaderboardService.getScore(this.user.userName));
    this.userRank = await firstValueFrom(this.leaderboardService.getUserRank(this.user.userName));

    this.topGameModeUsers = await firstValueFrom(this.leaderboardService.getGameModePodium(3));
    this.topRefactoringUsers = await firstValueFrom(this.leaderboardService.getRefactoringExercisePodium(3));

    this.leaderboardService.getAllUserSubmitHistoryByUserId(this.user.userId).subscribe(
      result => {
        this.userSubmitHistory = result;
        console.log("History: ", this.userSubmitHistory);
        if (this.userSubmitHistory.length > 0) {
          this.renderGameCharts();
        }
      }
    );

    //console.log('userRank:', this.userRank);
    //console.log('topGameModeUsers', this.topGameModeUsers);
  }

  isMissionCompleted(missionConfiguration: MissionConfiguration): boolean {
    if (this.userMissionsStatus === undefined)
      return false;

    const mission = this.userMissionsStatus.find(
      mission => mission.missionId === missionConfiguration.id
    );
    return mission ? mission.steps === missionConfiguration.steps.length : false;
  }

  private setUserLevel(): void {
    for (let i = 0; i < this.config.expValues.length; i++) {
      if (this.user.exp < this.config.expValues[i]) {
        this.userLevel = '⭐'.repeat(i + 1);
        return;
      }
    }

    this.userLevel = '⭐'.repeat(this.config.expValues.length + 1);
    return;
  }

  nextBadge() {
    for (let i = 0; i < this.config.badgeValues.length; i++) {
      if (this.user.exp < this.config.badgeValues[i].points) {
        return {
          name: this.config.badgeValues[i].name,
          pointsRequired: this.config.badgeValues[i].points,
        };
      }
    }
    return null;
  }

  nextLevelExpRequired(): number {
    for (let i = 0; i < this.config.expValues.length; i++) {
      if (this.user.exp < this.config.expValues[i]) {
        return this.config.expValues[i];
      }
    }

    return -1;
  }

  getProgressPercentage(): number {
    let nextBadge = this.nextBadge();

    if (!nextBadge) return 100;

    const currentBadgeIndex = this.config.badgeValues.findIndex(badge => badge.points === nextBadge!.pointsRequired) - 1;
    const currentBadgePoints = currentBadgeIndex >= 0 ? this.config.badgeValues[currentBadgeIndex].points : 0;

    const nextBadgePoints = nextBadge!.pointsRequired;

    const progress = ((this.user.exp - currentBadgePoints) / (nextBadgePoints - currentBadgePoints)) * 100;

    return Math.min(Math.max(progress, 0), 100);
  }

  private renderGameCharts(): void {
    const canvasCheckGame = document.getElementById('checkGameScoreChart') as HTMLCanvasElement;
    const canvasRefactoringGame = document.getElementById('refactoringGameScoreChart') as HTMLCanvasElement;

    if (this.chartInstanceCheckGame) {
      this.chartInstanceCheckGame.destroy(); // Distruggi il grafico precedente per evitare duplicati
    }
    if (this.chartInstanceCheckGame) {
      this.chartInstanceCheckGame.destroy(); // Distruggi il grafico precedente per evitare duplicati
    }

    const checkGameHistory = this.userSubmitHistory.filter(history => history.exerciseType === "check-smell");
    const refactoringGameHistory = this.userSubmitHistory.filter(history => history.exerciseType === "refactoring");
    Chart.register(ChartDataLabels);

    this.renderChart(checkGameHistory, canvasCheckGame);
    this.renderChart(refactoringGameHistory, canvasRefactoringGame);
  }

  private renderChart(history: UserSubmitHistory[], canvas: HTMLCanvasElement): void {
    console.log("checkGameHistory", history);
    this.chartInstanceCheckGame = new Chart(canvas, {
      type: 'line',
      data: {
        labels: history.map(history =>
          new Date(history.dateTime[0], history.dateTime[1] - 1, history.dateTime[2], history.dateTime[3], history.dateTime[4]).toLocaleDateString()
        ),
        datasets: [
          {
            label: '', // Rimuoviamo "Exercises Scores"
            data: history.map(history => history.exerciseScore),
            borderColor: '#4caf50',
            backgroundColor: 'rgba(76, 175, 80, 0.2)',
            borderWidth: 2,
            pointBackgroundColor: '#4caf50',
            pointRadius: 5
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: false // Nascondiamo la legenda dato che non serve più
          },
          tooltip: {
            enabled: true,
            callbacks: {
              title: (tooltipItems) => {
                const index = tooltipItems[0].dataIndex;
                return history[index].exerciseName; // Mostra il nome dell'esercizio nel tooltip
              },
              label: (tooltipItem) => `Score: ${tooltipItem.formattedValue}` // Mostra il punteggio
            }
          },
          datalabels: {
            align: 'top',
            anchor: 'end',
            formatter: (value, context) => {
              return history[context.dataIndex].exerciseName; // Nome esercizio sopra i punti
            },
            font: {
              weight: 'bold'
            },
            color: '#000'
          }
        },
        scales: {
          x: {
            title: {
              display: true,
              text: 'Date'
            }
          },
          y: {
            title: {
              display: true,
              text: 'Score'
            },
            beginAtZero: true
          }
        }
      },
      plugins: [ChartDataLabels]
    });
  }

  protected readonly environment = environment;
  protected readonly Object = Object;

}
