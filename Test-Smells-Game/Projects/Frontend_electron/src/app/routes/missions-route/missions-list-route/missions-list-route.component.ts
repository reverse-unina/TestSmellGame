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
          console.log(err.error.message || 'An unexpected error occurred');
          this.serverError = err.error.message || 'An unexpected error occurred';
        }
      });

      this.userMissionsStatus = await firstValueFrom(this.userService.getUserMissionsStatus());
      console.log("userMissionsStatus: ", this.userMissionsStatus);
    } catch (error) {
      console.error('Error occurred while fetching missions:', error);
    }
  }

  getMissionTags(): Set<string> {
    let tags: Set<string> = new Set();

    this.missions.forEach(mission => {
      if (mission.tag) {
        tags.add(mission.tag);
      }
    })

    return tags;
  }

  getMissionsByTag(tag: string): MissionConfiguration[] {
    let filteredMissions: MissionConfiguration[] = [];

    this.missions.forEach(mission => {
      if (mission.tag === tag)
        filteredMissions.push(mission);
    })

    return filteredMissions.sort((a, b) => {
      const aCompleted: boolean = this.isMissionCompleted(a);
      const bCompleted: boolean = this.isMissionCompleted(b);
      const aUnlocked: boolean = this.isMissionUnlocked(a);
      const bUnlocked: boolean = this.isMissionUnlocked(b);

      if (aCompleted && !bCompleted) return -1;
      if (!aCompleted && bCompleted) return 1;

      if (aUnlocked && !bUnlocked) return -1;
      if (!aUnlocked && bUnlocked) return 1;

      if (!aUnlocked && !bUnlocked) {
        return a.unlockAfter.length - b.unlockAfter.length;
      }

      return a.name.localeCompare(b.name);
    });

  }

  getMissionsWithoutTag() {
    let filteredMissions: MissionConfiguration[] = [];

    this.missions.forEach(mission => {
      if (!mission.tag)
        filteredMissions.push(mission);
    })

    return filteredMissions.sort((a, b) => {
      const aCompleted: boolean = this.isMissionCompleted(a);
      const bCompleted: boolean = this.isMissionCompleted(b);
      const aUnlocked: boolean = this.isMissionUnlocked(a);
      const bUnlocked: boolean = this.isMissionUnlocked(b);

      if (aCompleted && !bCompleted) return -1;
      if (!aCompleted && bCompleted) return 1;

      if (aUnlocked && !bUnlocked) return -1;
      if (!aUnlocked && bUnlocked) return 1;

      if (!aUnlocked && !bUnlocked) {
        return a.unlockAfter.length - b.unlockAfter.length;
      }

      return a.name.localeCompare(b.name);
    });
  }

  getRequiredMissions(mission: MissionConfiguration): MissionConfiguration[] {
    let missionsRequired: MissionConfiguration[] = [];

    if (mission.unlockAfter && mission.unlockAfter.length > 0) {
      this.missions.forEach(m => {
        if (mission.unlockAfter.find(unlock => unlock === m.id))
          missionsRequired.push(m);
      })
    }

    return missionsRequired;
  }

  getMissionNameById(missionId: string): string {
    return <string>this.missions.find(mission => mission.id === missionId)?.name;
  }

  isMissionUnlocked(mission: MissionConfiguration): boolean {
    let unlocked: undefined | boolean = true;
    let missionsRequired: MissionConfiguration[] = this.getRequiredMissions(mission);

    missionsRequired.forEach((missionConfiguration) => {
      const found = this.userMissionsStatus.find(missionStatus => missionStatus.missionId === missionConfiguration.id);
      unlocked &&= (found && found.steps === missionConfiguration.steps.length);
    });

    return unlocked;
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
