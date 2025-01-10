import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment.prod";
import {HttpClient} from "@angular/common/http";
import {tap, BehaviorSubject} from "rxjs";
import {Exercise} from "../../model/exercise/refactor-exercise.model";
import {RefactoringGameExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";

@Injectable({
  providedIn: 'root'
})
export class CodeeditorService {

  private codeModifiedSubject = new BehaviorSubject<boolean>(false);
  codeModified$ = this.codeModifiedSubject.asObservable();

  constructor(private http: HttpClient) { }

  setCodeModified(isModified: boolean) {
      this.codeModifiedSubject.next(isModified);
    }

  compile(exercise: Exercise, configuration: RefactoringGameExerciseConfiguration) {

    const body = {
      exerciseName: exercise.exerciseName,
      originalProductionCode: exercise.originalProductionCode,
      originalTestCode: exercise.originalTestCode,
      refactoredTestCode: exercise.refactoredTestCode,
      exerciseConfiguration: configuration.refactoringGameConfiguration
    }
    return this.http.post(environment.compilerServiceUrl+'/compiler/refactoring',body)
  }
}
