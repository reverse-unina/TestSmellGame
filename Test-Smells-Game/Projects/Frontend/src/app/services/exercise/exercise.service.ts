import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment.prod";
import { Observable } from 'rxjs';
import { ToolConfig } from "src/app/model/toolConfig/tool.config.model"
import {LearningContent} from "../../model/learningContent/learning-content";
import {
  CheckGameExerciseConfiguration,
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
    return this.http.get<CheckGameExerciseConfiguration[]>(environment.exerciseServiceUrl + '/exercises/checksmell')
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
    return this.http.get<CheckGameExerciseConfiguration>(environment.exerciseServiceUrl + '/exercises/checksmell/' + exerciseId, HTTPOptions);
  }

  getLeaningContentById(learningId: string): Observable<LearningContent> {
    return this.http.get<LearningContent>(environment.exerciseServiceUrl + `/exercises/learning/${learningId}`);
  }

  getToolConfig(): Observable<ToolConfig> {
    const HTTPOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'json' as 'json'
    };
    return this.http.get<ToolConfig>(environment.exerciseServiceUrl + '/toolconfig/', HTTPOptions);
  }


  logEvent(gameMode: string, player: string, eventDescription: string): Observable<any> {
    const eventLog = {
      gameMode,
      player,
      eventDescription,
      timestamp: new Date().toISOString()
    };
    console.log("body: ", eventLog);
    return this.http.post(environment.exerciseServiceUrl + '/files/logger', eventLog, { responseType: 'json' });
  }

  submitCheckSmellExercise(gameMode: string, studentName: string, exerciseId: string, results: string) {
    const formData = new FormData();
    formData.append('gameMode', gameMode);
    formData.append('studentName', studentName);
    formData.append('exerciseId', exerciseId);
    formData.append('results', new Blob([results], { type: 'text/plain' }), `${studentName}_results.txt`);

    return this.http.post(environment.exerciseServiceUrl + '/exercises/checksmell/submit/', formData);
  }

  submitRefactoringExercise(gameMode: string, studentName: string, exerciseId: string, productionCode: string, testCode: string, shellCode: string, results: string): Observable<any> {
    const formData = new FormData();
    formData.append('gameMode', gameMode);
    formData.append('studentName', studentName);
    formData.append('exerciseId', exerciseId);
    formData.append('productionCode', new Blob([productionCode], { type: 'text/plain' }), `${studentName}_ClassCode.java`);
    formData.append('testCode', new Blob([testCode], { type: 'text/plain' }), `${studentName}_TestCode.java`);
    formData.append('shellCode', new Blob([shellCode], { type: 'text/plain' }), `${studentName}_ShellCode.java`);
    formData.append('results', new Blob([results], { type: 'text/plain' }), `${studentName}_results.txt`);

    console.log("submitting");

    return this.http.post(environment.exerciseServiceUrl + '/exercises/refactoring/submit/', formData);
  }



}
