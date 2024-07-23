import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from "rxjs";
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from "../../model/user/user.model";
import { HttpClient } from '@angular/common/http';
import { environment } from "../../../environments/environment.prod";
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model";

interface Config {
  expValues: number[];
  badgeValues: number[];
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private config!: levelConfig;
  private baseUrl = environment.userServiceUrl + '/users/';

  user = new BehaviorSubject<User>(new User());
  constructor(private snackBar: MatSnackBar, private http: HttpClient) {
    this.initLevelConfig();
  }

  async initLevelConfig() {
      // @ts-ignore
      await import('src/assets/assets/level_config.json').then((data) => {
        this.config = data;
      });
    }

  getCurrentUser(): Observable<User> {
    return this.user.asObservable();
  }

  getUserExp(): number {
    return this.user.value.exp;
  }

  increaseUserExp(): void {
    const currentUser = this.user.value;
    currentUser.exp += 1;
    this.user.next(currentUser);

    switch (currentUser.exp) {
      case this.config.expValues[0]:
        this.showSnackBar('Congratulations! You have reached level 2');
        break;
      case this.config.expValues[1]:
        this.showSnackBar('Congratulations! You have reached level 3');
        break;
      case this.config.badgeValues[0]:
      case this.config.badgeValues[1]:
      case this.config.badgeValues[2]:
        this.showSnackBar('Congratulations! You have unlocked a new badge, view it on your profile page');
        break;
      default:
        break;
    }

    this.http.post(`${this.baseUrl}updateExp`, currentUser).subscribe(
      response => {
        console.log('User updated successfully', response);
      },
      error => {
        console.error('Error occurred during updating user', error);
      }
    );
  }

  updateUser(newUser: User): void {
    this.user.next(newUser);
  }

  private showSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      duration: 3000,
      verticalPosition: 'bottom',
      horizontalPosition: 'center'
    });
  }
}
