import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../model/user/user.model';
import { HttpClient } from '@angular/common/http';
import {levelConfig} from "src/app/model/levelConfiguration/level.configuration.model"

@Component({
  selector: 'app-profile-route',
  templateUrl: './profile-route.component.html',
  styleUrls: ['./profile-route.component.css']
})
export class ProfileRouteComponent implements OnInit {

  config!: levelConfig;
  user!: User;
  userLevel!: string;

  constructor(private userService: UserService, private http: HttpClient) {
    this.initLevelConfig();
    }

  async initLevelConfig() {
        // @ts-ignore
        await import('src/assets/assets/level_config.json').then((data) => {
          this.config = data;
        });
      }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.setUserLevel();
    });
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
