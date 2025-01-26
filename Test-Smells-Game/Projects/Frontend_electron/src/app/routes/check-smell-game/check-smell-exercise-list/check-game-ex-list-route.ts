import {Component, NgZone, OnInit, ViewChild} from '@angular/core';
import {ExerciseService} from 'src/app/services/exercise/exercise.service';
import {UserService} from 'src/app/services/user/user.service'
import {Router} from "@angular/router";
import {ElectronService} from "ngx-electron";
import { HttpClient } from '@angular/common/http';
import {FormBuilder, NgForm} from "@angular/forms";
import {CheckGameExerciseConfiguration} from 'src/app/model/exercise/ExerciseConfiguration.model';
import {GithubRetrieverComponent} from "../../../components/github-retriever/github-retriever.component";
import {firstValueFrom} from "rxjs";
import {ToolConfig} from "../../../model/toolConfig/tool.config.model";

@Component({
  selector: 'app-check-smell-game-exercise-list',
  templateUrl: './check-game-ex-list-route.component.html',
  styleUrls: ['./check-game-ex-list-route.component.css']
})
export class CheckGameExListRoute implements OnInit {
  config!: ToolConfig;
  exercises: CheckGameExerciseConfiguration[] = [];
  exercisesFromLocal: CheckGameExerciseConfiguration[] = [];
  serverError: string | undefined;
  waitingForServer!: boolean;
  enableGit = false;
  gitForm: any;
  exerciseType !: number;
  @ViewChild('child') child!: GithubRetrieverComponent;

  constructor(private exerciseService: ExerciseService,
              protected userService: UserService,
              private _electronService: ElectronService,
              private zone:NgZone,
              private _router: Router,
              private fb: FormBuilder,
              private http: HttpClient
  ) { }

  async ngOnInit(): Promise<void> {
    this.exerciseType = Number(localStorage.getItem("exerciseRetrieval"));

    console.log("Check smell list")

    // GET EXERCISES LIST FROM CLOUD
    if (this.exerciseType == 2){
      this.waitingForServer = true;
      this.exerciseService.getCheckGameExercises().subscribe({
        next: (response: CheckGameExerciseConfiguration[]) => {
          this.waitingForServer = false;
          this.serverError = undefined;
          this.exercises = response.sort(
            (a, b) => {
              const byLevel = a.checkGameConfiguration.level - b.checkGameConfiguration.level;
              if (byLevel !== 0)
                return byLevel;

              return a.exerciseId > b.exerciseId ? 1 : -1;
            });
          console.log(response);
        },
        error: (err) => {
          this.waitingForServer = false;
          this.serverError = err.error.message || 'An unexpected error occurred';
        }
      });
    } else if (this.exerciseType == 1){
      // GET EXERCISES LIST FROM GIT
      this.waitingForServer = true;
      this.exerciseService.getCheckGameExercises().subscribe({
        next: (response: CheckGameExerciseConfiguration[]) => {
          this.waitingForServer = false;
          this.serverError = undefined;
          this.exercises = response.sort(
            (a, b) => {
              const byLevel = a.checkGameConfiguration.level - b.checkGameConfiguration.level;
              if (byLevel !== 0)
                return byLevel;

              return a.exerciseId > b.exerciseId ? 1 : -1;
            });
          console.log(response);
        },
        error: (err) => {
          this.waitingForServer = false;
          this.serverError = err.error.message || 'An unexpected error occurred';
        }
      });
      this.enableGetExercisesFromGit()
      this._electronService.ipcRenderer.on('getCheckSmellExercisesFromLocal', (event, data: CheckGameExerciseConfiguration[])=>{
        this.zone.run(()=>{
          if (data instanceof Map) {
            this.waitingForServer = false;
            // @ts-ignore
            this.serverError = data.get("message") || 'An unexpected error occurred';
            this.child.stopLoading();
          } else {
            data.forEach(d => this.exercisesFromLocal.push(CheckGameExerciseConfiguration.fromJson(d)));
            this.child.stopLoading()
            console.log("Exercises received: ", this.exercisesFromLocal)
          }
        });
      });
    }

    this.config = await firstValueFrom(this.exerciseService.getToolConfig());
  }

  private enableGetExercisesFromGit() {
    this.enableGit = true;
    this.gitForm = this.fb.group({
      url:"https://github.com/mick0974/TestSmellGame-Exercises",
      branch:"main"
    });
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

  isExerciseInLocal(exerciseId: string) {
    return this.exercises.find(e => e.exerciseId === exerciseId) !== undefined;
  }

}
