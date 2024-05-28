import {Component, NgZone, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {LeaderboardService} from "../../services/leaderboard/leaderboard.service";
import {Solution} from "../../model/solution/solution";
import {ExerciseService} from "../../services/exercise/exercise.service";
import {ExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";

@Component({
  selector: 'app-leaderboard-route',
  templateUrl: './leaderboard-route.component.html',
  styleUrls: ['./leaderboard-route.component.css']
})
export class LeaderboardRouteComponent implements OnInit {

  solutions!: Solution[]
  exerciseCode!: string;
  testingCode!: string;
  exerciseName = this.route.snapshot.params['exercise'];
  isAutoValutative!: boolean;
  exerciseConfiguration!: ExerciseConfiguration;


  constructor(private http: HttpClient,
              private router: Router,
              private leaderboardService: LeaderboardService,
              private exerciseService: ExerciseService,
              private route:ActivatedRoute) {
    // GET TESTING CLASS FROM ELECTRON
  }

  ngOnInit(): void {
    this.getConfiguration();

    if(!this.isAutoValutative)
      this.retrieveCode();

  }

  retrieveCode(){
    this.exerciseService.getMainClass(this.exerciseName).subscribe( data=> {
      this.exerciseCode = data;
    });
    this.exerciseService.getTestClass(this.exerciseName).subscribe( data => {
      this.testingCode = data
    })
  }
  setupConfigFiles(data:any){
    this.exerciseConfiguration = data;
    this.isAutoValutative = this.exerciseConfiguration.auto_valutative;
    this.leaderboardService.getSolutionsByExerciseName(this.exerciseConfiguration.exerciseId).subscribe(data=>{
      this.solutions = data;
    })

  }

  getConfiguration() {
    this.exerciseService.getConfigFile(this.exerciseName).subscribe(data=>{this.setupConfigFiles(data)})
  }
}
