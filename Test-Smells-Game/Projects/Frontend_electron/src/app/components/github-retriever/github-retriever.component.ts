import {Component, Input, NgZone, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ExerciseService} from "../../services/exercise/exercise.service";
import {ElectronService} from "ngx-electron";
import {Router} from "@angular/router";
import {FormBuilder, NgForm} from "@angular/forms";
import {ProgressBarMode} from "@angular/material/progress-bar";
import {environment} from "../../../environments/environment.prod";

@Component({
  selector: 'app-github-retriever',
  templateUrl: './github-retriever.component.html',
  styleUrls: ['./github-retriever.component.css']
})
export class GithubRetrieverComponent implements OnInit, OnChanges {

  constructor(private exerciseService: ExerciseService,
              private _electronService: ElectronService,
              private zone: NgZone,
              private _router: Router,
              private fb: FormBuilder,
  ) {
  }

  exercises = new Array<any>();
  enableGit = false
  gitForm: any;
  exerciseType !: number;
  @Input() progressBarMode!: ProgressBarMode;

  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.progressBarMode)
  }

  ngOnInit(): void {
    this.progressBarMode = 'determinate'
    this.fillFormValues()
  }


  private fillFormValues() {
    this.gitForm = this.fb.group({
      url:"https://github.com/DarioTin/Tesi-Exercises-Repository",
      branch:"exercises"
    })
  }

  prepareGetFilesFromRemote(form: NgForm){
    this.exercises = [];
    this.startLoading()
    this.exerciseService.getFilesFromRemote(form.value.url, form.value.branch)
    environment.repositoryUrl = form.value.url;
    environment.repositoryBranch = form.value.branch;
  }

  startLoading(){
    this.progressBarMode = 'query'
  }

  stopLoading() {
    this.progressBarMode = 'determinate'
  }
}
