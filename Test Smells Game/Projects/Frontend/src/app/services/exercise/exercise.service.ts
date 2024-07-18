import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {catchError, switchMap} from 'rxjs/operators';
import {environment} from "../../../environments/environment.prod";
import {ExerciseConfiguration} from 'src/app/model/exercise/ExerciseConfiguration.model';
import { Observable, forkJoin } from 'rxjs';

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

}
