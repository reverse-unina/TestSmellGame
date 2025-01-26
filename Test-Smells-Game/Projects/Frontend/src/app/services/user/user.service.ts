import { Injectable } from '@angular/core';
import {BehaviorSubject, firstValueFrom, lastValueFrom, Observable} from "rxjs";
import { User } from "../../model/user/user.model";
import { HttpClient } from '@angular/common/http';
import { environment } from "../../../environments/environment.prod";
import { ToolConfig } from "src/app/model/toolConfig/tool.config.model";
import { ExerciseService } from "src/app/services/exercise/exercise.service"
import {MissionStatus} from "../../model/missions/mission.model";

interface Config {
  expValues: number[];
  badgeValues: number[];
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private config!: ToolConfig;
  private userHasLevelledUp: boolean = false;
  private userHasUnlockedBadge: boolean = false;

  user = new BehaviorSubject<User>(new User());
  constructor(private exerciseService: ExerciseService,
              private http: HttpClient) {
  }

  getCurrentUser(): Observable<User> {
    return this.user.asObservable();
  }

  getUserExp(): number {
    return this.user.value.exp;
  }

  async increaseUserExp(exp: number): Promise<void> {
    this.userHasLevelledUp = false;
    this.userHasUnlockedBadge = false;

    const currentUser = this.user.value;
    this.config = await lastValueFrom(this.exerciseService.getToolConfig());

    if (!currentUser) {
      console.error('No user found!');
      return;
    }

    let oldUserLevel = 0, i = 0;
    const oldUserExp = currentUser.exp;
    for (i; i < this.config.expValues.length; i++) {
      if (oldUserExp < this.config.expValues[i]) {
        oldUserLevel = i+1;
        break;
      }
    }
    if (oldUserLevel == 0) {
      oldUserLevel = this.config.expValues.length;
    }

    // Increase user exp
    currentUser.exp += exp;
    this.user.next(currentUser);

    // Update user exp on server
    this.http.post(`${environment.userServiceUrl}/users/updateExp`, currentUser).subscribe({
      next: response => console.log('User updated successfully:', response),
      error: error => console.error('Error occurred during updating user:', error)
    });

    // Check if user has leveled up or unlocked badge
    let currentUserLevel = 0; i = 0;
    if (this.config) {
      // Check if user levelled up
      for (i; i < this.config.expValues.length; i++) {
        if (this.user.value.exp < this.config.expValues[i]) {
          currentUserLevel = i + 1;
          break;
        }
      }

      if (currentUserLevel == 0) {
        currentUserLevel = this.config.expValues.length;
      }

      if (currentUserLevel > oldUserLevel) {
        this.userHasLevelledUp = true;
      }

      this.userHasUnlockedBadge = this.config.badgeValues.some(
        badge => badge.points === currentUser.exp
      );
    }
  }

  hasUserLevelledUp(): boolean {
    return this.userHasLevelledUp;
  }

  hasUserUnlockedBadge(): boolean {
    return this.userHasUnlockedBadge;
  }

  getUserMissionsStatus(): Observable<MissionStatus[]> {
    return this.http.get<MissionStatus[]>(`${environment.userServiceUrl}/users/${this.user.value.userId}/missions`);
  }

  updateUserMissionStatus(missionId: string, steps: number): Observable<any> {
    const currentUser = this.user.value;

    // Update frontend mission status
    if (currentUser.missionsStatus) {
      const currentMissionStatus = currentUser.missionsStatus.find(mission => mission.missionId === missionId);
      if (currentMissionStatus) {
        currentUser.missionsStatus.map(mission => {
          mission.missionId === missionId ? new MissionStatus(missionId, steps) : mission;
        });
      } else {
        console.log("Current user: ", currentUser)
        currentUser.missionsStatus.push({missionId: missionId, steps: steps});
      }
    } else {
      currentUser.missionsStatus = [{missionId: missionId, steps: steps}];
    }

    this.user.next(currentUser);

    // Update user mission status on server
    const updatedUser = {
      "email": currentUser.email,
      "missionStatus": {missionId: missionId, steps: steps}
    };

    return this.http.put(`${environment.userServiceUrl}/users/missions`, updatedUser);
  }


  /*
  increaseUserExp(): string | undefined {
    const currentUser = this.user.value;
    currentUser.exp += 1;
    this.user.next(currentUser);

    console.log("Envs: ", environment);
    console.log("user-service: ", environment.userServiceUrl + '/users/');
    this.http.post(`${environment.userServiceUrl}/users/updateExp`, currentUser).subscribe(
      response => {
        console.log('User updated successfully', response);
      },
      error => {
        console.error('Error occurred during updating user', error);
      }
    );

    this.exerciseService.getLevelConfig()
      .then(
        (data: Observable<levelConfig>) => {
          data.forEach(data => this.config = data);
          let message: string | undefined;
          if (this.config) {
            console.log("currentUser.exp", currentUser.exp);
            switch (currentUser.exp) {
              case this.config.expValues[0]:
                this.exerciseService.logEvent(currentUser.userName, 'Reached level 2').subscribe({
                  next: response => console.log('Log event response:', response),
                  error: error => console.error('Error submitting log:', error)
                });
                message = `You have reached <strong>level 2</strong>!`;
                console.log("switch1: ", message);
                break;
              case this.config.expValues[1]:
                this.exerciseService.logEvent(currentUser.userName, 'Reached level 3').subscribe({
                  next: response => console.log('Log event response:', response),
                  error: error => console.error('Error submitting log:', error)
                });
                message = `You have reached <strong>level 3</strong>!`;
                console.log("switch2: ", message);
                break;
              default:
                if (this.config.badgeValues.hasOwnProperty(currentUser.exp)) {
                  this.exerciseService.logEvent(currentUser.userName, 'Unlocked a new badge').subscribe({
                    next: response => console.log('Log event response:', response),
                    error: error => console.error('Error submitting log:', error)
                  });
                }
                message = `You have unlocked a new badge, view it on your profile page!`;
                console.log("switch3: ", message);
                break;
            }
          }

          console.log("level up message: ", message);
          return message;
        }
      )
      .catch(
        error => {
          console.error('Error fetching level config:', error);
    });

    return undefined;
  }

   */

}
