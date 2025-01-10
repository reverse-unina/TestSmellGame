import {Component, HostListener, OnInit, ViewChild, OnDestroy, ChangeDetectorRef} from '@angular/core';
import {ActivatedRoute, Event, Router} from '@angular/router';
import {RefactoringService} from "../../../services/refactoring/refactoring.service";
import {CodeeditorService} from "../../../services/codeeditor/codeeditor.service";
import {ExerciseService} from "../../../services/exercise/exercise.service";
import {LeaderboardService} from "../../../services/leaderboard/leaderboard.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserService} from "../../../services/user/user.service";

@Component({
  selector: 'app-refactoring-game-core',
  templateUrl: './refactoring-game-core-route.component.html',
  styleUrls: ['./refactoring-game-core-route.component.css']
})
export class RefactoringGameCoreRouteComponent implements OnInit, OnDestroy {
  @ViewChild('code') code: any;
  @ViewChild('testing') testing: any;
  @ViewChild('output') output: any;

  exerciseName!: string;
  protected refactoringService!: RefactoringService;
  serverError: string | undefined;

  constructor(
    private route: ActivatedRoute,
    private codeService: CodeeditorService,
    private exerciseService: ExerciseService,
    private leaderboardService: LeaderboardService,
    private snackBar: MatSnackBar,
    private userService: UserService,
    private router: Router,
  ) {
    this.refactoringService = new RefactoringService(
      this.codeService,
      this.exerciseService,
      this.leaderboardService,
      this.snackBar,
      this.userService
    );
    this.exerciseName = this.route.snapshot.params['exercise'];
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

  submitIsEnabled():boolean {
    return this.refactoringService.progressBarMode == 'query';
  }

  compile(): void {
    this.refactoringService.compileExercise(this.testing.editorComponent)
  }


  protected readonly Math = Math;
}
