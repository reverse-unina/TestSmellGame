import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {MissionConfiguration} from "../../model/missions/mission.model";
import {environment} from "../../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class MissionService {

  constructor(private http: HttpClient) {}

  getMissions(): Observable<MissionConfiguration[]> {
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });
    return this.http.get<MissionConfiguration[]>(environment.exerciseServiceUrl + `/missions/`, { headers: headers });
  }

  getMissionById(missionId: string): Observable<MissionConfiguration> {
    const headers = new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });
    return this.http.get<MissionConfiguration>(environment.exerciseServiceUrl + `/missions/${missionId}`, { headers: headers });
  }
}
