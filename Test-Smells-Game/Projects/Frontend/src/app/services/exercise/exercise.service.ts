import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, switchMap} from 'rxjs/operators';
import {environment} from "../../../environments/environment.prod";
import {ExerciseConfiguration} from 'src/app/model/exercise/ExerciseConfiguration.model';
import { Observable, forkJoin } from 'rxjs';
import { levelConfig } from "src/app/model/levelConfiguration/level.configuration.model"

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {

  constructor(private http: HttpClient) { }

  getExercises(){
    return this.http.get<any>(environment.exerciseServiceUrl + '/files/')
  }

  getMainClass(exercise: string){
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text'
    }
    return this.http.get<string>(environment.exerciseServiceUrl + '/files/' + exercise + "/Production", HTTPOptions);
  }

  getTestClass(exercise: string){
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text'
    }
    return this.http.get<string>(environment.exerciseServiceUrl + '/files/' + exercise + "/Test", HTTPOptions);

  }
  getConfigFile(exercise: string) {
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'json'
    }
    return this.http.get<any>(environment.exerciseServiceUrl + '/files/' + exercise + "/Configuration", HTTPOptions);
  }

    getAllConfigFiles(): Observable<any[]> {
      return this.http.get<any[]>(environment.exerciseServiceUrl + '/files/configurations');
    }

  getLevelConfig(): Observable<levelConfig> {
      const HTTPOptions = {
        headers: new HttpHeaders({
          'Content-Type': 'application/json'
        }),
        responseType: 'json' as 'json'
      };
      return this.http.get<levelConfig>(environment.exerciseServiceUrl + '/levelconfig/get-levelconfig', HTTPOptions);
    }

  getBadgeUrl(badgeName: string): string {
      return environment.exerciseServiceUrl + '/levelconfig/badge/' + badgeName;
    }

  logEvent(player: string, eventDescription: string): Observable<any> {
      const eventLog = {
        player,
        eventDescription,
        timestamp: new Date().toISOString()
      };
      return this.http.post(environment.exerciseServiceUrl + '/files/log-event', eventLog, { responseType: 'json' });
    }

}
