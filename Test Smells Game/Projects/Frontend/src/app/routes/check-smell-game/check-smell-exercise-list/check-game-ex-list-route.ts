import {Component, NgZone, OnInit} from '@angular/core';
import {ExerciseService} from 'src/app/services/exercise/exercise.service';
import {Router} from "@angular/router";
import {FormBuilder, NgForm} from "@angular/forms";
import {environment} from "../../../../environments/environment.prod";

@Component({
  selector: 'app-check-smell-game-exercise-list',
  templateUrl: './check-game-ex-list-route.component.html',
  styleUrls: ['./check-game-ex-list-route.component.css']
})
export class CheckGameExListRoute implements OnInit {

  constructor(private exerciseService: ExerciseService,
              private zone:NgZone,
              private _router: Router,
              private fb: FormBuilder

  ) { }
  exercises = new Array<any>();
  serverProblems = false;
  waitingForServer!: boolean;


  ngOnInit(): void {
    this.waitingForServer = true;
    this.exerciseService.getExercises().subscribe(response =>{
      this.waitingForServer = false;
      this.exercises = response;
      }, error => {
      this.serverProblems = true;
      this.waitingForServer = false;
      });
  }

}
