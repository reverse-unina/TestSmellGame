import { Component, NgZone, OnInit } from '@angular/core';
import { ExerciseService } from 'src/app/services/exercise/exercise.service';
import { Router } from "@angular/router";
import { ExerciseConfiguration } from 'src/app/model/exercise/ExerciseConfiguration.model';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-refactoring-game-exercise-list',
  templateUrl: './refactoring-game-ex-list-route.component.html',
  styleUrls: ['./refactoring-game-ex-list-route.component.css']
})
export class RefactoringGameExListRouteComponent implements OnInit {

  constructor(private exerciseService: ExerciseService,
              private userService: UserService,
              private zone: NgZone,
              private _router: Router) { }

  exercises = new Array<any>();
  exerciseConfigurations = new Array<ExerciseConfiguration>();
  serverProblems = false;
  waitingForServer!: boolean;

  ngOnInit(): void {
    this.waitingForServer = true;

    this.exerciseService.getAllConfigFiles().subscribe(
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

    this.exerciseService.getExercises().subscribe(
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
      if (this.userService.getUserExp() < 5) {
        return level === 1;
      } else if (this.userService.getUserExp() < 15) {
        return level <= 2;
      } else {
        return true;
      }
    }
}
