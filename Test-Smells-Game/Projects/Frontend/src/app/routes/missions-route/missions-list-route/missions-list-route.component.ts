import {Component, OnInit} from '@angular/core';
import {MissionService} from "../../../services/missions/mission.service";
import {MissionConfiguration, MissionStatus} from "../../../model/missions/mission.model";
import {UserService} from "../../../services/user/user.service";
import {firstValueFrom} from "rxjs";
import {CheckGameExerciseConfiguration} from "../../../model/exercise/ExerciseConfiguration.model";

@Component({
  selector: 'app-missions-list-route',
  templateUrl: './missions-list-route.component.html',
  styleUrls: ['./missions-list-route.component.css']
})
export class MissionsListRouteComponent implements OnInit {

  missions!: MissionConfiguration[];
  userMissionsStatus!: MissionStatus[];
  serverError:string | undefined = undefined;

  constructor(
    private missionsService: MissionService,
    private userService: UserService
  ) {}

  async ngOnInit(): Promise<void> {
    try {
      this.missionsService.getMissions().subscribe({
        next: (response: MissionConfiguration[]) => {
          this.serverError = undefined;
          this.missions = response;
        },
        error: (err) => {
          //console.log(err.error.message || 'An unexpected error occurred');
          this.serverError = err.error.message || 'An unexpected error occurred';
        }
      });

      this.userMissionsStatus = await firstValueFrom(this.userService.getUserMissionsStatus());
      //console.log("userMissionsStatus: ", this.userMissionsStatus);
    } catch (error) {
      console.error('Error occurred while fetching missions:', error);
    }
  }

  getMissionProgress(missionConfiguration: MissionConfiguration): number {
    if (this.userMissionsStatus === undefined)
      return 0;

    const mission = this.userMissionsStatus.find(
      mission => mission.missionId === missionConfiguration.id
    );
    return Math.round((mission ? mission.steps : 0) * 100 / missionConfiguration.steps.length);
  }

  isMissionCompleted(missionConfiguration: MissionConfiguration): boolean {
    if (this.userMissionsStatus === undefined)
      return false;

    const mission = this.userMissionsStatus.find(
      mission => mission.missionId === missionConfiguration.id
    );
    return mission ? mission.steps === missionConfiguration.steps.length : false;
  }

}
