import {Component, NgZone, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";
import {LeaderboardService} from "../../services/leaderboard/leaderboard.service";
import {CheckSmellStatistics, RefactoringSolution} from "../../model/solution/solution";
import {ExerciseService} from "../../services/exercise/exercise.service";
import {RefactoringGameExerciseConfiguration, CheckGameExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";

@Component({
  selector: 'app-leaderboard-route',
  templateUrl: './leaderboard-route.component.html',
  styleUrls: ['./leaderboard-route.component.css']
})
export class LeaderboardRouteComponent implements OnInit {

  refactoringSolutions!: RefactoringSolution[];
  checkSmellStatistics!: CheckSmellStatistics[];
  refactoringExerciseConfiguration!: RefactoringGameExerciseConfiguration;

  exerciseCode!: string;
  testingCode!: string;
  exerciseName = this.route.snapshot.params['exercise'];
  isAutoValutative!: boolean;

  isAssignmentsRoute!: boolean;
  isCheckSmellRoute!: boolean

  constructor(private router: Router,
              private leaderboardService: LeaderboardService,
              private exerciseService: ExerciseService,
              private route:ActivatedRoute)
  { }

  ngOnInit(): void {

    this.isCheckSmellRoute = this.router.url.includes('check-game');

    if (this.isCheckSmellRoute) {
      this.leaderboardService.getCheckSmellSolutionsByExerciseName(this.exerciseName).subscribe(
        data => {
          this.checkSmellStatistics = data;
        }
      );

    } else {
      this.exerciseService.getRefactoringGameConfigFile(this.exerciseName).subscribe(
        data=>{
          this.refactoringExerciseConfiguration = data;
          this.isAutoValutative = this.refactoringExerciseConfiguration.autoValutative;

          this.leaderboardService.getRefactoringSolutionByExerciseId(this.refactoringExerciseConfiguration.exerciseId).subscribe(
            solutions=>{
              this.refactoringSolutions = solutions;
              //console.log("Refactoring solutions received: ", solutions);
            });
        });

      if(!this.isAutoValutative) {
        this.exerciseService.getMainClass(this.exerciseName).subscribe( data=> {
          this.exerciseCode = data;
        });
        this.exerciseService.getTestClass(this.exerciseName).subscribe( data => {
          this.testingCode = data
        })
      }

    }
  }
}
