import {Component, NgZone, OnInit, ViewChild} from '@angular/core';
import {ExerciseService} from 'src/app/services/exercise/exercise.service';
import {UserService} from 'src/app/services/user/user.service'
import {Router} from "@angular/router";
import {ElectronService} from "ngx-electron";
import { HttpClient } from '@angular/common/http';
import {FormBuilder, NgForm} from "@angular/forms";
import {CheckGameExerciseConfig} from 'src/app/model/exercise/ExerciseConfiguration.model';
import {GithubRetrieverComponent} from "../../../components/github-retriever/github-retriever.component";
import {environment} from "../../../../environments/environment.prod";
import {levelConfig} from "src/app/model/levelConfiguration/level.configuration.model"
import {firstValueFrom} from "rxjs";

@Component({
  selector: 'app-check-smell-game-exercise-list',
  templateUrl: './check-game-ex-list-route.component.html',
  styleUrls: ['./check-game-ex-list-route.component.css']
})
export class CheckGameExListRoute implements OnInit {
  config!: levelConfig;
  exercises: CheckGameExerciseConfig[] = [];
  serverError: string | undefined;
  waitingForServer!: boolean;
  enableGit = false;
  gitForm: any;
  exerciseType !: number;
  @ViewChild('child') child!: GithubRetrieverComponent;

  constructor(private exerciseService: ExerciseService,
              private userService: UserService,
              private _electronService: ElectronService,
              private zone:NgZone,
              private _router: Router,
              private fb: FormBuilder,
              private http: HttpClient
  ) { }

  async ngOnInit(): Promise<void> {
    this.exerciseType = Number(localStorage.getItem("exerciseRetrieval"));

    // GET EXERCISES LIST FROM CLOUD
    if (this.exerciseType == 2){
      this.waitingForServer = true;
      this.exerciseService.getCheckGameExercises().subscribe({
        next: (response: CheckGameExerciseConfig[]) => {
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
    } else if (this.exerciseType == 1){
      // GET EXERCISES LIST FROM GIT
      this.waitingForServer = false;
      //this.initExercises();
      this.enableGetExercisesFromGit()
      this._electronService.ipcRenderer.on('getExerciseFilesFromLocal', (event, data)=>{
        this.zone.run(()=>{
          this.exercises = data;
          this.child.stopLoading()
        })
      })
    }
  }

  private enableGetExercisesFromGit() {
    this.enableGit = true
    this.gitForm = this.fb.group({
      url:"https://github.com/LZannini/Thesis-Exercises-Repository",
      branch:"exercises"
    })
  }

  prepareGetFilesFromRemote(form: NgForm){
    this.exercises = [];
    this.exerciseService.getFilesFromRemote(form.value.url, form.value.branch)
    environment.repositoryUrl = form.value.url;
    environment.repositoryBranch = form.value.branch;
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
