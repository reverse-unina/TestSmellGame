import {Component, NgZone, OnInit} from '@angular/core';
import {ExerciseService} from 'src/app/services/exercise/exercise.service';
import {UserService} from 'src/app/services/user/user.service'
import {CheckGameExerciseConfig,} from 'src/app/model/exercise/ExerciseConfiguration.model';
import {levelConfig} from "src/app/model/levelConfiguration/level.configuration.model"
import {firstValueFrom} from "rxjs";

@Component({
  selector: 'app-check-smell-game-exercise-list',
  templateUrl: './check-game-ex-list-route.component.html',
  styleUrls: ['./check-game-ex-list-route.component.css']
})
export class CheckGameExListRoute implements OnInit {
  config!: levelConfig;
  exercises: CheckGameExerciseConfig[] = [];
  serverError: string | undefined;
  waitingForServer!: boolean;

  constructor(private exerciseService: ExerciseService,
              private userService: UserService,
  ) { }

  async ngOnInit(): Promise<void> {
    this.waitingForServer = true;

    this.config = await firstValueFrom(this.exerciseService.getLevelConfig());

    this.exerciseService.getCheckGameExercises().subscribe({
      next: (response: CheckGameExerciseConfig[]) => {
        this.waitingForServer = false;
        this.serverError = undefined;
        this.exercises = response;
        console.log(response);
      },
      error: (err) => {
        this.waitingForServer = false;
        this.serverError = err.error.message || 'An unexpected error occurred';
      }
    });
  }

  isExerciseEnabled(level: number): boolean {
    if (this.userService.getUserExp() < this.config.expValues[0]) {
      return level === 1;
    } else if (this.userService.getUserExp() < this.config.expValues[1]) {
      return level <= 2;
    } else {
      return true;
    }
  }

}
