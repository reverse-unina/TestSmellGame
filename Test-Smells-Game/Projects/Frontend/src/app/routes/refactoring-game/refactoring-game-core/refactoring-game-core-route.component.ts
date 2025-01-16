import {Component, HostListener, OnInit, ViewChild, OnDestroy, ChangeDetectorRef} from '@angular/core';
import {ActivatedRoute, Event, Router} from '@angular/router';
import {RefactoringService} from "../../../services/refactoring/refactoring.service";
import {CodeeditorService} from "../../../services/codeeditor/codeeditor.service";
import {ExerciseService} from "../../../services/exercise/exercise.service";
import {LeaderboardService} from "../../../services/leaderboard/leaderboard.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../../../services/user/user.service";
import {User} from "../../../model/user/user.model";
import {AchievementAlertComponent} from "../../../components/alert/achivement-alert/achievement-alert.component";
import {FailAlertComponent} from "../../../components/alert/fail-alert/fail-alert.component";
import {SuccessAlertComponent} from "../../../components/alert/success-alert/success-alert.component";

@Component({
  selector: 'app-refactoring-game-core',
  templateUrl: './refactoring-game-core-route.component.html',
  styleUrls: ['./refactoring-game-core-route.component.css']
})
export class RefactoringGameCoreRouteComponent implements OnInit, OnDestroy {
  @ViewChild('code') code: any;
  @ViewChild('testing') testing: any;
  @ViewChild('output') output: any;

  @ViewChild('achievementAlert') achievementAlert!: AchievementAlertComponent;
  @ViewChild('failAlert') failAlert!: FailAlertComponent;
  @ViewChild('successAlert') successAlert!: SuccessAlertComponent;

  exerciseName!: string;
  protected refactoringService!: RefactoringService;
  serverError: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private codeService: CodeeditorService,
    private exerciseService: ExerciseService,
    private leaderboardService: LeaderboardService,
    private snackBar: MatSnackBar,
    private userService: UserService
  ) {
    this.refactoringService = new RefactoringService(
      this.codeService,
      this.exerciseService,
      this.leaderboardService,
      this.snackBar,
      this.userService
    );
    this.exerciseName = decodeURIComponent(this.route.snapshot.params['exercise']);
  }

  async ngOnInit(): Promise<void> {
    this.refactoringService.initSmellDescriptions();
    this.serverError = await this.refactoringService.initCodeFromCloud(this.exerciseName);
    this.refactoringService.restoreCode("refactoring-game", this.exerciseName);
  }

  ngOnDestroy(): void {
    if (this.testing)
      this.refactoringService.saveCode("refactoring-game", this.exerciseName, this.testing.editorComponent);

    //this.router.navigate(['refactoring-game']);
  }

  @HostListener('window:beforeunload', ['$event'])
  unloadHandler(event: Event) {
    this.refactoringService.saveCode("refactoring-game", this.exerciseName, this.testing.editorComponent);
  }

  submitIsDisabled():boolean {
    return this.refactoringService.progressBarMode == 'query' || !this.refactoringService.isExercisePassed();
  }

  compile(): void {
    this.refactoringService.compileExercise(this.testing.editorComponent).then(
      () => {
        if (this.refactoringService.isExercisePassed())
          this.successAlert.show();
        else
          this.failAlert.show();
      }
    );
  }

  publishSolution(): void {
    this.refactoringService.startLoading()

    const score: number = Math.abs(this.refactoringService.smellNumber - this.refactoringService.exerciseConfiguration.refactoringGameConfiguration.smellsAllowed);

    if(this.refactoringService.exerciseIsCompiledSuccessfully && this.refactoringService.isExercisePassed()){
      this.leaderboardService.saveSolution(this.refactoringService.compiledExercise,
        this.refactoringService.exerciseConfiguration,
        score,
        Boolean(this.refactoringService.refactoringResult),
        this.refactoringService.originalCoverage,
        this.refactoringService.refactoredCoverage,
        this.refactoringService.smells).subscribe(
        result => {
          this.refactoringService.showPopUp("Solution saved");
          this.refactoringService.stopLoading();
          this.userService.getCurrentUser().subscribe((user: User | any) => {
            this.refactoringService.user = user;
          });

          this.leaderboardService.getScore(this.userService.user.value.userName).subscribe(
            result => {
              const currentScore = result;
              this.leaderboardService.updateBestRefactoringScore(this.userService.user.value.userName, this.exerciseName, score).subscribe(
                (updatedScore) => {
                  console.log("Current score: ", currentScore);
                  console.log("Updated score: ", updatedScore);
                  if (updatedScore.refactoringScore > currentScore.refactoringScore) {
                    this.userService.increaseUserExp(updatedScore.refactoringScore - currentScore.refactoringScore).then(
                      success => {
                        if (this.userService.hasUserLevelledUp())
                          this.achievementAlert.show("Level Up!", "Congratulation, you have levelled up!");

                        if (this.userService.hasUserUnlockedBadge())
                          this.achievementAlert.show("Badge Unblocked!", "You have unlocked a new badge, view it on your profile page!");
                      }
                    );
                  }
                }
              );
            }
          );

          this.exerciseService.logEvent(this.userService.user.value.userName, 'Completed ' + this.exerciseName + ' in refactoring game mode').subscribe({
            next: response => console.log('Log event response:', response),
            error: error => console.error('Error submitting log:', error)
          });
        },error => {
          this.refactoringService.showPopUp("Server has a problem");
          this.refactoringService.stopLoading()
        });
    }else{
      this.refactoringService.showPopUp("To save your solution in solutions repository you have to complete the exercise");
      this.refactoringService.stopLoading()
    }
  }


  protected readonly Math = Math;
}
