import {Component, OnInit} from '@angular/core';
import {MissionService} from "../../../services/missions/mission.service";
import {MissionConfiguration, MissionStatus} from "../../../model/missions/mission.model";
import {UserService} from "../../../services/user/user.service";
import {firstValueFrom} from "rxjs";

@Component({
  selector: 'app-missions-list-route',
  templateUrl: './missions-list-route.component.html',
  styleUrls: ['./missions-list-route.component.css']
})
export class MissionsListRouteComponent implements OnInit {

  missionConfigurations!: MissionConfiguration[];
  userMissionsStatus!: MissionStatus[];
  errorMessage:string | undefined = undefined;
  errorParts:string[] = [];

  constructor(
    private missionsService: MissionService,
    private userService: UserService
  ) {}

  async ngOnInit(): Promise<void> {
    try {
      this.missionsService.getMissions().subscribe(
        data => {
          this.missionConfigurations = data;
          this.errorMessage = undefined;
          this.errorParts = [];
        },
        error => {
          this.errorMessage = error.error;
          console.error(this.errorMessage);
          this.refactorErrorMessage(this.errorMessage);
      })

      this.userMissionsStatus = await firstValueFrom(this.userService.getUserMissionsStatus());
      console.log("userMissionsStatus: ", this.userMissionsStatus);
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

  refactorErrorMessage(error: string | undefined) : void {
    if (error === undefined)
      return;

    const matchRegexMissingField = error.match(/Missing required property "(.*?)" in file (.*)/);
    const matchRegexUnrecognizedField = error.match(/Unrecognized field "(.*?)" not marked as ignorable found in file (.*)/);
    const matchRegexReadingFile = error.match(/Error reading assignment file (.*)/);

    if (matchRegexMissingField) {
      this.errorParts = [
        matchRegexMissingField[0].split(/ "(.*?)"/)[0],
        `"${matchRegexMissingField[1]}"`,
        "in file",
        matchRegexMissingField[2]
      ];
      console.log("Error Parts: ", this.errorParts);
    } else if (matchRegexUnrecognizedField) {
      this.errorParts = [
        matchRegexUnrecognizedField[0].split(/ "(.*?)"/)[0],
        `"${matchRegexUnrecognizedField[1]}"`,
        "not marked as ignorable found in file",
        matchRegexUnrecognizedField[2]
      ];
    } else if (matchRegexReadingFile) {
      this.errorParts = [
        matchRegexReadingFile[0].split(/ "(.*?)"/)[0],
        matchRegexReadingFile[1]
      ];
    } else {
      this.errorParts[0] = error;
    }
  }

}
