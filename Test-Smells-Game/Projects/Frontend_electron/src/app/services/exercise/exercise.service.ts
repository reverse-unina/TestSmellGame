import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment.prod";
import { Observable } from 'rxjs';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"
import {LearningContent} from "../../model/learningContent/learning-content";
import { ElectronService } from "ngx-electron";
import {Repository} from "../../model/repository/repository.model";
import {
  CheckGameExerciseConfig,
  RefactoringGameExerciseConfiguration
} from "../../model/exercise/ExerciseConfiguration.model";

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private http: HttpClient, private _electronService: ElectronService) { }

  getFilesFromRemote(githubUrl: string, branchName: string, exerciseType: string) {
    let repo = new Repository(branchName, githubUrl);
    this._electronService.ipcRenderer.send("getFilesFromRemote", repo, exerciseType);
  }

  initProductionCodeFromLocal(exercise: string) {
    console.log("Sending request for Production code");
    this._electronService.ipcRenderer.send('getProductionClassFromLocal', exercise, "Production");
  }

  initTestingCodeFromLocal(exercise: string) {
    console.log("Sending request for Test code");
    this._electronService.ipcRenderer.send('getTestingClassFromLocal', exercise, "Test");
  }

  initRefactoringExerciseConfigFromLocal(exercise: string) {
    //this._electronService.ipcRenderer.send('getConfigFilesFromLocal', exercise);
    console.log("Sending request for Config code");
    this._electronService.ipcRenderer.send('getRefactoringExerciseConfigFromLocal', exercise, 'Config');
  }

  initCheckSmellExerciseConfigFromLocal(exercise: string) {
    //this._electronService.ipcRenderer.send('getConfigFilesFromLocal', exercise, 'CheckGameExerciseConfig');
    this._electronService.ipcRenderer.send('getCheckGameExerciseConfigFromLocal', exercise, 'CheckGameExerciseConfig');
  }

  getRefactoringGameExercises(){
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });
    return this.http.get<RefactoringGameExerciseConfiguration[]>(environment.exerciseServiceUrl + '/exercises/refactoring', { headers: headers })
  }

  getCheckGameExercises(){
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });
    return this.http.get<CheckGameExerciseConfig[]>(environment.exerciseServiceUrl + '/exercises/checksmell', { headers: headers })
  }

  getMainClass(exerciseId: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true'
    });
    let HTTPOptions: Object = {
      headers: headers,
      responseType: 'text'
    };
    return this.http.get<string>(environment.exerciseServiceUrl + '/exercises/refactoring/' + exerciseId + "/Production", HTTPOptions);
  }

  getTestClass(exerciseId: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true'
    });
    let HTTPOptions: Object = {
      headers: headers,
      responseType: 'text'
    };
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
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true'
    });
    let HTTPOptions: Object = {
      headers: headers,
      responseType: 'json'
    };
    return this.http.get<CheckGameExerciseConfig>(environment.exerciseServiceUrl + '/exercises/checksmell/' + exerciseId, HTTPOptions);
  }

  getLeaningContentById(learningId: string): Observable<LearningContent> {
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });
    return this.http.get<LearningContent>(environment.exerciseServiceUrl + `/exercises/learning/${learningId}`, {headers});
  }

  getLevelConfig(): Observable<levelConfig> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true'
    });
    const HTTPOptions = {
      headers: headers,
      responseType: 'json' as 'json'
    };
    return this.http.get<levelConfig>(environment.exerciseServiceUrl + '/levelconfig/', HTTPOptions);
  }


  logEvent(player: string, eventDescription: string): Observable<any> {
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });
    const eventLog = {
      player,
      eventDescription,
      timestamp: new Date().toISOString()
    };
    return this.http.post(environment.exerciseServiceUrl + '/files/logger', eventLog, { headers, responseType: 'json' });
  }
}
