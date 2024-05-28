import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {Exercise} from "../../model/exercise/refactor-exercise.model";
import {ExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";

@Injectable({
  providedIn: 'root'
})
export class CodeeditorService {

  constructor(private http: HttpClient) { }

  compile(exercise: Exercise, configuration: ExerciseConfiguration) {

    const body = {
      exerciseName: exercise.exerciseName,
      originalProductionCode: exercise.originalProductionCode,
      originalTestCode: exercise.originalTestCode,
      refactoredTestCode: exercise.refactoredTestCode,
      exerciseConfiguration: configuration.refactoring_game_configuration
    }
    return this.http.post(environment.compilerServiceUrl+'/compiler/refactoring',body)
  }
}
