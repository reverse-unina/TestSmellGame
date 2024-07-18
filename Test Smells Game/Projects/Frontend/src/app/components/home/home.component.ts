import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.prod";
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  isEnvironmentFormEnabled: boolean = false;
  environmentForm: any;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    environment.tokenServiceUrl = "";
  }

  enableEnvironmentForm() {
    this.isEnvironmentFormEnabled = !this.isEnvironmentFormEnabled;
    this.environmentForm = this.fb.group({
      user_service: environment.userServiceUrl,
      compiler_service: environment.compilerServiceUrl,
      exercise_service: environment.exerciseServiceUrl,
      leaderboard_service: environment.leaderboardServiceUrl
    })

  }

  setupNewEnvironmentFromForm(environmentForm: any) {
    console.log(environmentForm)
    environment.userServiceUrl = environmentForm.value.user_service
    environment.compilerServiceUrl = environmentForm.value.compiler_service
    environment.leaderboardServiceUrl = environmentForm.value.leaderboard_service
    environment.exerciseServiceUrl = environmentForm.value.exercise_service
  }
}
