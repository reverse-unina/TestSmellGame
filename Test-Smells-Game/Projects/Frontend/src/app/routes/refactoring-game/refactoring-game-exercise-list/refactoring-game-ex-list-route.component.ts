import { Component, NgZone, OnInit } from '@angular/core';
import { ExerciseService } from 'src/app/services/exercise/exercise.service';
import { Router } from "@angular/router";
import { HttpClient } from '@angular/common/http';
import { RefactoringGameExerciseConfiguration } from 'src/app/model/exercise/ExerciseConfiguration.model';
import { UserService } from 'src/app/services/user/user.service';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"
import {firstValueFrom} from "rxjs";

@Component({
  selector: 'app-refactoring-game-exercise-list',
  templateUrl: './refactoring-game-ex-list-route.component.html',
  styleUrls: ['./refactoring-game-ex-list-route.component.css']
})
export class RefactoringGameExListRouteComponent implements OnInit {

  constructor(private exerciseService: ExerciseService,
              protected userService: UserService,
              ) { }

  protected config!: levelConfig;
  exercises: RefactoringGameExerciseConfiguration[] = [];
  serverError: string | undefined;
  waitingForServer!: boolean;

  async ngOnInit(): Promise<void> {
    this.waitingForServer = true;

    this.exerciseService.getRefactoringGameExercises().subscribe({
      next: (response: RefactoringGameExerciseConfiguration[]) => {
        this.waitingForServer = false;
        this.serverError = undefined;
        this.exercises = response;
        console.log(this.exercises);
      },
      error: (err) => {
        this.waitingForServer = false;
        this.serverError = err.error.message || 'An unexpected error occurred';
      }
    });

    this.config = await firstValueFrom(this.exerciseService.getLevelConfig());
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
