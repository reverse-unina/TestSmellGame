import {Component, NgZone, OnInit} from '@angular/core';
import {ExerciseService} from 'src/app/services/exercise/exercise.service';
import {UserService} from 'src/app/services/user/user.service'
import {CheckGameExerciseConfiguration,} from 'src/app/model/exercise/ExerciseConfiguration.model';
import {ToolConfig} from "src/app/model/toolConfig/tool.config.model"
import {firstValueFrom} from "rxjs";

@Component({
  selector: 'app-check-smell-game-exercise-list',
  templateUrl: './check-game-ex-list-route.component.html',
  styleUrls: ['./check-game-ex-list-route.component.css']
})
export class CheckGameExListRoute implements OnInit {
  config!: ToolConfig;
  exercises: CheckGameExerciseConfiguration[] = [];
  serverError: string | undefined;
  waitingForServer!: boolean;

  constructor(private exerciseService: ExerciseService,
              private userService: UserService,
  ) { }

  async ngOnInit(): Promise<void> {
    this.waitingForServer = true;

    this.config = await firstValueFrom(this.exerciseService.getToolConfig());

    this.exerciseService.getCheckGameExercises().subscribe({
      next: (response: CheckGameExerciseConfiguration[]) => {
        this.waitingForServer = false;
        this.serverError = undefined;
        this.exercises = response.sort(
          (a, b) => {
            const byLevel = a.checkGameConfiguration.level - b.checkGameConfiguration.level;
            if (byLevel !== 0)
              return byLevel;

            return a.exerciseId > b.exerciseId ? 1 : -1;
        });
        console.log(response);
      },
      error: (err) => {
        this.waitingForServer = false;
        this.serverError = err.error.message || 'An unexpected error occurred';
      }
    });
  }

  isExerciseEnabled(level: number): boolean {
    const userExp = this.userService.user.value.exp;
    let userLevel = 0;
    for (let i = 0; i < this.config.expValues.length; i++) {
      if (userExp < this.config.expValues[i]) {
        userLevel = i+1;
        break;
      }
    }
    if (userLevel == 0) {
      userLevel = this.config.expValues.length;
    }

    return userLevel >= level;
  }

}
