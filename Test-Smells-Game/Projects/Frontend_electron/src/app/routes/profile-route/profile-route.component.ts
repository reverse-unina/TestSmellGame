import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../model/user/user.model';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"
import { ExerciseService } from '../../services/exercise/exercise.service'
import {firstValueFrom} from "rxjs";
import {MissionService} from "../../services/missions/mission.service";
import {MissionConfiguration, MissionStatus} from "../../model/missions/mission.model";
import {environment} from "../../../environments/environment.prod";
import {LeaderboardService} from "../../services/leaderboard/leaderboard.service";
import {PodiumRanking, Score, UserRanking} from "../../model/rank/score";

@Component({
  selector: 'app-profile-route',
  templateUrl: './profile-route.component.html',
  styleUrls: ['./profile-route.component.css']
})
export class ProfileRouteComponent implements OnInit {

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



  readonly environment = environment;
  readonly Object = Object;
}
