import { Component, NgZone, OnInit } from '@angular/core';
import { ExerciseService } from 'src/app/services/exercise/exercise.service';
import { Router } from "@angular/router";
import { HttpClient } from '@angular/common/http';
import { RefactoringGameExerciseConfiguration } from 'src/app/model/exercise/ExerciseConfiguration.model';
import { UserService } from 'src/app/services/user/user.service';
import { ToolConfig } from "src/app/model/toolConfig/tool.config.model"
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

  protected config!: ToolConfig;
  exercises: RefactoringGameExerciseConfiguration[] = [];
  serverError: string | undefined;
  waitingForServer!: boolean;

  async ngOnInit(): Promise<void> {
    this.waitingForServer = true;

    this.exerciseService.getRefactoringGameExercises().subscribe({
      next: (response: RefactoringGameExerciseConfiguration[]) => {
        this.waitingForServer = false;
        this.serverError = undefined;
        this.exercises = response.sort(
          (a, b) => {
            const byLevel = a.refactoringGameConfiguration.level - b.refactoringGameConfiguration.level;
            if (byLevel !== 0)
              return byLevel;

            return a.exerciseId > b.exerciseId ? 1 : -1;
          }).filter(exercise => exercise.availableForGame);
        //console.log(this.exercises);
      },
      error: (err) => {
        this.waitingForServer = false;
        this.serverError = err.error.message || 'An unexpected error occurred';
      }
    });

    this.config = await firstValueFrom(this.exerciseService.getToolConfig());
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
