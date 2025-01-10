import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {MissionConfiguration} from "../../model/missions/mission.model";
import {environment} from "../../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class MissionService {

  constructor(private http: HttpClient) {}

  getMissions(): Observable<MissionConfiguration[]> {
    return this.http.get<MissionConfiguration[]>(environment.exerciseServiceUrl + `/missions/`);
  }

  getMissionById(missionId: string): Observable<MissionConfiguration> {
    return this.http.get<MissionConfiguration>(environment.exerciseServiceUrl + `/missions/${missionId}`);
  }
}
