import {Component, NgZone, OnInit} from '@angular/core';
import {ExerciseService} from 'src/app/services/exercise/exercise.service';
import {UserService} from 'src/app/services/user/user.service'
import {Router} from "@angular/router";
import {FormBuilder, NgForm} from "@angular/forms";
import {ExerciseConfiguration} from 'src/app/model/exercise/ExerciseConfiguration.model';
import {environment} from "../../../../environments/environment.prod";

@Component({
  selector: 'app-check-smell-game-exercise-list',
  templateUrl: './check-game-ex-list-route.component.html',
  styleUrls: ['./check-game-ex-list-route.component.css']
})
export class CheckGameExListRoute implements OnInit {

  constructor(private exerciseService: ExerciseService,
              private userService: UserService,
              private zone:NgZone,
              private _router: Router,
              private fb: FormBuilder

  ) { }
  exercises = new Array<any>();
  exerciseConfigurations = new Array<ExerciseConfiguration>();
  serverProblems = false;
  waitingForServer!: boolean;


  ngOnInit(): void {
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

    this.waitingForServer = true;
    this.exerciseService.getExercises().subscribe(response =>{
      this.waitingForServer = false;
      this.exercises = response;
      }, error => {
      this.serverProblems = true;
      this.waitingForServer = false;
      });
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
