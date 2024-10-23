import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from 'rxjs';
import { ElectronService } from "ngx-electron";
import {Repository} from "../../model/repository/repository.model";
import { environment } from "../../../environments/environment.prod";
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model";

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private http: HttpClient, private _electronService: ElectronService) { }

  getFilesFromRemote(githubUrl: string, branchName: string) {
    let repo = new Repository(branchName, githubUrl);
    this._electronService.ipcRenderer.send("getFilesFromRemote", repo);
  }

  initProductionCodeFromLocal(exercise: string) {
    this._electronService.ipcRenderer.send('getProductionClassFromLocal', exercise);
  }

  initTestingCodeFromLocal(exercise: string) {
    this._electronService.ipcRenderer.send('getTestingClassFromLocal', exercise);
  }

  initConfigCodeFromLocal(exercise: string) {
    this._electronService.ipcRenderer.send('getConfigFilesFromLocal', exercise);
  }

  getExercises() {
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'  
    });
    return this.http.get<any>(environment.exerciseServiceUrl + '/files/', { headers });
  }

  getMainClass(exercise: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true'  
    });
    let HTTPOptions: Object = {
      headers: headers,
      responseType: 'text'
    };
    return this.http.get<string>(environment.exerciseServiceUrl + '/files/' + exercise + "/Production", HTTPOptions);
  }

  getTestClass(exercise: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true' 
    });
    let HTTPOptions: Object = {
      headers: headers,
      responseType: 'text'
    };
    return this.http.get<string>(environment.exerciseServiceUrl + '/files/' + exercise + "/Test", HTTPOptions);
  }

  getConfigFile(exercise: string) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true'  
    });
    let HTTPOptions: Object = {
      headers: headers,
      responseType: 'json'
    };
    return this.http.get<any>(environment.exerciseServiceUrl + '/files/' + exercise + "/Configuration", HTTPOptions);
  }

  getAllConfigFiles(): Observable<any[]> {
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'  
    });
    return this.http.get<any[]>(environment.exerciseServiceUrl + '/files/configurations', { headers });
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
    return this.http.get<levelConfig>(environment.exerciseServiceUrl + '/levelconfig/get-levelconfig', HTTPOptions);
  }

  getBadgeUrl(badgeName: string): string {
    return environment.exerciseServiceUrl + '/levelconfig/badge/' + badgeName;
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
    return this.http.post(environment.exerciseServiceUrl + '/files/log-event', eventLog, { headers, responseType: 'json' });
  }
}
