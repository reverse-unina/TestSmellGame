import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../model/user/user.model';
import { HttpClient } from '@angular/common/http';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"
import { ExerciseService } from '../../services/exercise/exercise.service'

@Component({
  selector: 'app-profile-route',
  templateUrl: './profile-route.component.html',
  styleUrls: ['./profile-route.component.css']
})
export class ProfileRouteComponent implements OnInit {

  config!: levelConfig;
  user!: User;
  userLevel!: string;

  constructor(private userService: UserService, private exerciseService: ExerciseService, private http: HttpClient) {

    }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
    });
    this.exerciseService.getLevelConfig().subscribe(
              (data: levelConfig) => {
                  this.config = data;
                  this.setUserLevel();
                  console.log('LevelConfig:', this.config);
              },
              error => {
                  console.error('Error fetching level config:', error);
          });
  }

  getBadgeValue(index: number): string {
    const keys = Object.keys(this.config.badgeValues).map(key => Number(key));
    if (index >= 0 && index < keys.length) {
      const key = keys[index];
      return this.config.badgeValues[key];
    }
    throw new Error('Indice fuori dal range');
  }


  getBadgeValueKey(index: number): number {
      const keys = Object.keys(this.config.badgeValues).map(key => Number(key));
      return keys[index];
    }

  getBadgeUrl(badgeName: string): string {
      return this.exerciseService.getBadgeUrl(badgeName);
    }

  private setUserLevel(): void {
    if (this.user.exp < this.config.expValues[0]) {
      this.userLevel = '⭐';
    } else if (this.user.exp >= this.config.expValues[0] && this.user.exp < this.config.expValues[1]) {
      this.userLevel = '⭐⭐';
    } else if (this.user.exp >= this.config.expValues[1]){
      this.userLevel = '⭐⭐⭐';
    }
  }
}
