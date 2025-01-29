import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../model/user/user.model';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"
import { ExerciseService } from '../../services/exercise/exercise.service'
import { firstValueFrom, Subject, takeUntil, tap } from "rxjs";
import { MissionService } from "../../services/missions/mission.service";
import { MissionConfiguration, MissionStatus } from "../../model/missions/mission.model";
import { environment } from "../../../environments/environment.prod";
import { LeaderboardService } from "../../services/leaderboard/leaderboard.service";
import { PodiumRanking, Score, UserRanking } from "../../model/rank/score";
import { TestService } from 'src/app/services/test/test.service';
import Chart from 'chart.js/auto';
import { TestHistory } from 'src/app/model/test-history';

@Component({
  selector: 'app-profile-route',
  templateUrl: './profile-route.component.html',
  styleUrls: ['./profile-route.component.css']
})
export class ProfileRouteComponent implements OnInit, OnDestroy {

  config!: levelConfig;
  user!: User;
  userLevel!: string;
  missionConfigurations!: MissionConfiguration[];
  userMissionsStatus!: MissionStatus[];
  userScore!: Score;
  userRank!: UserRanking;
  topGameModeUsers!: PodiumRanking;
  topRefactoringUsers!: PodiumRanking;
  serverError: string | undefined;

  private destroy$ = new Subject<void>();

  public testScores: number[] = [];
  public testDates: string[] = [];
  private chartInstance: Chart | null = null;


  constructor(
    private userService: UserService,
    private exerciseService: ExerciseService,
    private missionService: MissionService,
    private leaderboardService: LeaderboardService,
    private testService: TestService
  ) { }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete
  }

  async ngOnInit(): Promise<void> {
    this.userService.getCurrentUser().pipe(
      tap((user): void => {
        this.user = user;
      }, 
      takeUntil(this.destroy$))
    ).subscribe();

    this.config = await firstValueFrom(this.exerciseService.getLevelConfig());
    this.setUserLevel();
    console.log('LevelConfig:', this.config);

    try {
      this.missionConfigurations = await firstValueFrom(this.missionService.getMissions());
      this.userMissionsStatus = await firstValueFrom(this.userService.getUserMissionsStatus());
      this.serverError = undefined;
    } catch (error) {
      console.log(error);
      // @ts-ignore
      this.serverError = error.error.message || 'An unexpected error occurred';
    }

    this.userScore = await firstValueFrom(this.leaderboardService.getScore(this.user.userName));
    this.userRank = await firstValueFrom(this.leaderboardService.getUserRank(this.user.userName));

    this.topGameModeUsers = await firstValueFrom(this.leaderboardService.getGameModePodium(3));
    this.topRefactoringUsers = await firstValueFrom(this.leaderboardService.getRefactoringExercisePodium(3));

    console.log('userRank:', this.userRank);
    console.log('topGameModeUsers', this.topGameModeUsers);

    this.fetchTestHistory();
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



  protected readonly environment = environment;
  protected readonly Object = Object;


  ngAfterViewInit(): void {
    this.renderChart();
  }

  private fetchTestHistory(): void {
    this.testService.loadTestHistoryFromServer(this.user.userId)
      .pipe(
        tap(e => {

          const history: TestHistory[] = e;
          this.testScores = history.map(test => test.totalScore); // Usa il punteggio totale di ogni test
          this.testDates = history.map(test => new Date(test.date).toLocaleDateString()); // Usa la data del test
          //this.renderChart();

        }),
        takeUntil(this.destroy$)
      )
      .subscribe()

  }


  private renderChart(): void {
    const canvas = document.getElementById('scoreChart') as HTMLCanvasElement;

    if (this.chartInstance) {
      this.chartInstance.destroy(); // Distruggi il grafico precedente per evitare duplicati
    }

    this.chartInstance = new Chart(canvas, {
      type: 'line',
      data: {
        labels: this.testDates,
        datasets: [
          {
            label: 'Test Scores',
            data: this.testScores,
            borderColor: '#4caf50',
            backgroundColor: 'rgba(76, 175, 80, 0.2)',
            borderWidth: 2
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true,
            position: 'top'
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
      }
    });
  }

}



