import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment.prod";
import { Observable } from 'rxjs';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"
import {LearningContent} from "../../model/learningContent/learning-content";
import {
  CheckGameExerciseConfig,
  RefactoringGameExerciseConfiguration
} from "../../model/exercise/ExerciseConfiguration.model";

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private http: HttpClient) { }

  getRefactoringGameExercises(){
    return this.http.get<RefactoringGameExerciseConfiguration[]>(environment.exerciseServiceUrl + '/exercises/refactoring')
  }

  getCheckGameExercises(){
    return this.http.get<CheckGameExerciseConfig[]>(environment.exerciseServiceUrl + '/exercises/checksmell')
  }

  getMainClass(exerciseId: string){
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text'
    }
    return this.http.get<string>(environment.exerciseServiceUrl + '/exercises/refactoring/' + exerciseId + "/Production", HTTPOptions);
  }

  getTestClass(exerciseId: string){
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text'
    }
    return this.http.get<string>(environment.exerciseServiceUrl + '/exercises/refactoring/' + exerciseId + "/Test", HTTPOptions);

  }

  getRefactoringGameConfigFile(exerciseId: string) {
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'json'
    }
    return this.http.get<RefactoringGameExerciseConfiguration>(environment.exerciseServiceUrl + '/exercises/refactoring/' + exerciseId + "/Configuration", HTTPOptions);
  }

  getCheckGameConfigFile(exerciseId: string) {
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'json'
    }
    return this.http.get<CheckGameExerciseConfig>(environment.exerciseServiceUrl + '/exercises/checksmell/' + exerciseId, HTTPOptions);
  }

  getLeaningContentById(learningId: string): Observable<LearningContent> {
    return this.http.get<LearningContent>(environment.exerciseServiceUrl + `/exercises/learning/${learningId}`);
  }

  getLevelConfig(): Observable<levelConfig> {
    const HTTPOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'json' as 'json'
    };
    return this.http.get<levelConfig>(environment.exerciseServiceUrl + '/levelconfig/', HTTPOptions);
  }


  logEvent(player: string, eventDescription: string): Observable<any> {
      const eventLog = {
        player,
        eventDescription,
        timestamp: new Date().toISOString()
      };
      return this.http.post(environment.exerciseServiceUrl + '/files/logger', eventLog, { responseType: 'json' });
    }


  // Recupera esercizi casuali di uno specifico livello (multi-level/{level})
  getExercisesByLevel(level: number, count: number = 5): Observable<any> {
    return this.http.get<any>(environment.exerciseServiceUrl + `/files/multi-level/${level}?count=${count}`);
  }

}
