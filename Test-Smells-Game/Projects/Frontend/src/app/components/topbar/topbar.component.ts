import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {Subscription} from "rxjs";
import {UserService} from "../../services/user/user.service";
import {ExerciseService} from "../../services/exercise/exercise.service";
import {User} from "../../model/user/user.model";
import {HttpClient} from '@angular/common/http';
import {ToolConfig} from "src/app/model/toolConfig/tool.config.model"

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit, OnDestroy {
  config!: ToolConfig;
  isAuthenticated = false;
  currentUser: User | null = null;

  private userSub: Subscription | undefined;
  constructor(private userService: UserService,
              private exerciseService: ExerciseService,
              private authService: AuthService,
              private http: HttpClient) {

              }



  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.isAuthenticated = !!user;
      this.currentUser = user;
    });
    //console.log("Component created");
    this.exerciseService.getToolConfig().subscribe(
                              (data: ToolConfig) => {
                                  this.config = data;
                                  //console.log('toolConfig:', this.config);
                              },
                              error => {
                                  console.error('Error fetching level config:', error);
                          });
  }

  ngOnDestroy(): void {
    if (this.userSub) {
      this.userSub.unsubscribe();
    }
    //console.log("Component destroyed");
  }

  onLogout() {
    this.authService.logout();
  }

  getStars(exp: number): string {
    let stars;
    for (let i = 0; i < this.config.expValues.length; i++) {
      if (this.currentUser!.exp < this.config.expValues[i]) {
        stars = '⭐'.repeat(i + 1);
        return stars;
      }
    }

    stars = '⭐'.repeat(this.config.expValues.length + 1);
    return stars;
  }
}
