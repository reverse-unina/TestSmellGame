import { Component, HostListener, OnInit } from '@angular/core';
import { CodeeditorService } from "../../../services/codeeditor/codeeditor.service";
import { ExerciseService } from "../../../services/exercise/exercise.service";
import { ActivatedRoute } from "@angular/router";
import { Question } from "../../../model/question/question.model";
import { Answer } from "../../../model/question/answer.model";
import { CheckGameExerciseConfig } from "../../../model/exercise/ExerciseConfiguration.model";
import { User } from "../../../model/user/user.model";
import { MatCheckbox } from "@angular/material/checkbox";
import { UserService } from '../../../services/user/user.service';
import { MatSnackBar } from "@angular/material/snack-bar";
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"

@Component({
  selector: 'app-check-game-core',
  templateUrl: './check-game-core-route.component.html',
  styleUrls: ['./check-game-core-route.component.css']
})
export class CheckGameCoreRouteComponent implements OnInit {
  ngOnInit(): void {
  }










}
