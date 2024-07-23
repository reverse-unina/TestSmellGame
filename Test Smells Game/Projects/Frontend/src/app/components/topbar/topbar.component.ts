import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {Subscription} from "rxjs";
import {UserService} from "../../services/user/user.service";
import {User} from "../../model/user/user.model";
import {HttpClient} from '@angular/common/http';
import {levelConfig} from "src/app/model/levelConfiguration/level.configuration.model"

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit, OnDestroy {
  config!: levelConfig;
  isAuthenticated = false;
  currentUser: User | null = null;

  private userSub: Subscription | undefined;
  constructor(private userService: UserService,
              private authService: AuthService,
              private http: HttpClient) {
                this.initLevelConfig();
              }

            async initLevelConfig() {
                  // @ts-ignore
                  await import('src/assets/assets/level_config.json').then((data) => {
                    this.config = data;
                  });
                }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.isAuthenticated = !!user;
      this.currentUser = user;
    });
    console.log("Component created");
  }

  ngOnDestroy(): void {
    if (this.userSub) {
      this.userSub.unsubscribe();
    }
    console.log("Component destroyed");
  }

  onLogout() {
    this.authService.logout();
  }

  getStars(exp: number): string {
      if (exp < this.config.expValues[0]) {
        return '⭐';
      } else if (exp < this.config.expValues[1]) {
        return '⭐⭐';
      } else {
        return '⭐⭐⭐';
      }
    }
}
