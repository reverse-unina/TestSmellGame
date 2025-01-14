import { Component, NgZone, OnInit, ViewChild } from '@angular/core';
import { ExerciseService } from 'src/app/services/exercise/exercise.service';
import {ElectronService} from "ngx-electron";
import {FormBuilder, NgForm} from "@angular/forms";
import {RefactoringGameExerciseConfiguration} from 'src/app/model/exercise/ExerciseConfiguration.model';
import { UserService } from 'src/app/services/user/user.service';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"
import {GithubRetrieverComponent} from "../../../components/github-retriever/github-retriever.component";
import {firstValueFrom} from "rxjs";


@Component({
  selector: 'app-refactoring-game-exercise-list',
  templateUrl: './refactoring-game-ex-list-route.component.html',
  styleUrls: ['./refactoring-game-ex-list-route.component.css']
})
export class RefactoringGameExListRouteComponent implements OnInit {

  constructor(private exerciseService: ExerciseService,
              private _electronService: ElectronService,
              public userService: UserService,
              private zone: NgZone,
              private fb: FormBuilder) { }

  config!: levelConfig;
  exercises: RefactoringGameExerciseConfiguration[] = [];
  exercisesFromLocal: RefactoringGameExerciseConfiguration[] = [];
  serverError: string | undefined;
  waitingForServer!: boolean;
  enableGit = false
  gitForm: any;
  exerciseType !: number;
  @ViewChild('child') child!: GithubRetrieverComponent;

  async ngOnInit(): Promise<void> {
    this.exerciseType = Number(localStorage.getItem("exerciseRetrieval"));

    // GET EXERCISES LIST FROM CLOUD
    if (this.exerciseType == 2){
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
    } else if (this.exerciseType == 1){
      // GET EXERCISES LIST FROM GIT
      this.waitingForServer = true;
      this.exerciseService.getRefactoringGameExercises().subscribe({
        next: (response: RefactoringGameExerciseConfiguration[]) => {
          this.waitingForServer = false;
          this.serverError = undefined;
          this.exercises = response;
          console.log(response);
        },
        error: (err) => {
          this.waitingForServer = false;
          this.serverError = err.error.message || 'An unexpected error occurred';
        }
      });
      this.enableGetExercisesFromGit();
      this._electronService.ipcRenderer.on('getFilesFromLocal', (event, data: any)=>{
        this.zone.run(()=>{
          if (data instanceof Map) {
            this.waitingForServer = false;
            // @ts-ignore
            this.serverError = data.get("message") || 'An unexpected error occurred';
            this.child.stopLoading();
          } else {
            // @ts-ignore
            data.forEach(d => this.exercisesFromLocal.push(RefactoringGameExerciseConfiguration.fromJson(d)));
            this.child.stopLoading();
            console.log("Exercises received: ", this.exercisesFromLocal)
          }
        });
      });

    }

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

  private enableGetExercisesFromGit() {
    this.enableGit = true;
    this.gitForm = this.fb.group({
      url:"https://github.com/mick0974/TestSmellGame-Exercises",
      branch:"main"
    });
  }

  isExerciseInLocal(exerciseId: string) {
    return this.exercises.find(e => e.exerciseId === exerciseId) !== undefined;
  }
}
