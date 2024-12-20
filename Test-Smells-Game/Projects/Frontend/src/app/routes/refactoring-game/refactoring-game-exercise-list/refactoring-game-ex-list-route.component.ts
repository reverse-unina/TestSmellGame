import { Component, NgZone, OnInit } from '@angular/core';
import { ExerciseService } from 'src/app/services/exercise/exercise.service';
import { Router } from "@angular/router";
import { HttpClient } from '@angular/common/http';
import { RefactoringGameExerciseConfiguration } from 'src/app/model/exercise/ExerciseConfiguration.model';
import { UserService } from 'src/app/services/user/user.service';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"

@Component({
  selector: 'app-refactoring-game-exercise-list',
  templateUrl: './refactoring-game-ex-list-route.component.html',
  styleUrls: ['./refactoring-game-ex-list-route.component.css']
})
export class RefactoringGameExListRouteComponent implements OnInit {

  constructor(private exerciseService: ExerciseService,
              private userService: UserService,
              private zone: NgZone,
              private _router: Router,
              private http: HttpClient) { }

  private config!: levelConfig;
  exercises = new Array<any>();
  exerciseConfigurations = new Array<RefactoringGameExerciseConfiguration>();
  serverProblems = false;
  waitingForServer!: boolean;

  ngOnInit(): void {
    this.waitingForServer = true;

    this.exerciseService.getAllRefactoringGameConfigFiles().subscribe(
      (response: any[]) => {
        this.waitingForServer = false;
        this.exerciseConfigurations = response.map(item => JSON.parse(atob(item)));
        console.log(this.exerciseConfigurations);
      },
      error => {
        this.serverProblems = true;
        this.waitingForServer = false;
      }
    );

    this.exerciseService.getLevelConfig().subscribe(
          (data: levelConfig) => {
            this.config = data;
            console.log('LevelConfig:', this.config);
          },
          error => {
            console.error('Error fetching level config:', error);
          }
        );

    this.exerciseService.getRefactoringGameExercises().subscribe(
      response => {
        this.waitingForServer = false;
        this.exercises = response;
      },
      error => {
        this.serverProblems = true;
        this.waitingForServer = false;
      }
    );
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
