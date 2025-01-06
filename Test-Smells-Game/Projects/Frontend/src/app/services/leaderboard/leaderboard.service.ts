import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment.prod";
import {Solution} from "../../model/solution/solution";
import {Exercise} from "../../model/exercise/refactor-exercise.model";
import {UserService} from "../user/user.service";
import {RefactoringGameExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";
import {Rank, UserRanking} from "../../model/rank/rank";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {

  constructor(private http: HttpClient, private userService: UserService) { }

  getSolutionsByExerciseName(exercise: string){
    return this.http.get<Solution[]>(environment.leaderboardServiceUrl + '/leaderboard/' + exercise);
  }

  saveSolution(exercise: Exercise,
               exerciseConfiguration: RefactoringGameExerciseConfiguration,
               score: number,
               refactoringResult: boolean,
               originalCoverage: number,
               refactoredCoverage:number,
               smells: Object){
    console.log(environment.leaderboardServiceUrl+'/leaderboard/')
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
    }
    const body = {
      "exerciseId": exerciseConfiguration.exerciseId,
      "playerName": this.userService.user.getValue().userName,
      "refactoredCode": exercise.refactoredTestCode,
      "score": score,
      "refactoringResult": refactoringResult,
      "originalCoverage": originalCoverage,
      "refactoredCoverage": refactoredCoverage,
      "smells": smells
    }
    return this.http.post(environment.leaderboardServiceUrl+'/leaderboard/', body, HTTPOptions)
  }

  postComment(comment: string, solutionId: number, commentAuthor: string){
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
    }
    const body = {
      "commentAuthor" : commentAuthor,
      "commentText" : comment,
      "solutionId": solutionId
    }
    return this.http.post(environment.leaderboardServiceUrl + "/leaderboard/solution/postComment", body, HTTPOptions)
  }

  voteSolution(solutionId: number, userId: number, vote: string){
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
    }
    const body = {
      "solutionId" : solutionId,
      "userId" : userId,
      "voteType" : vote
    }
    return this.http.post(environment.leaderboardServiceUrl+'/leaderboard/solution/' + solutionId, body, HTTPOptions)
  }

  getVoteForUser(solutionId: number, userId: number){
    return this.http.get(environment.leaderboardServiceUrl + '/leaderboard/solution/' + solutionId + '/'+  userId);
  }


  /* Rank methods */
  getScore(userId: number): Observable<Rank> {
    return this.http.get<Rank>(`${environment.leaderboardServiceUrl}/rank/${userId}`);
  }


  updateScore(userId: number, gameMode: string, score: number): Observable<Rank> {
    const params = new HttpParams()
      .set('gameMode', gameMode)
      .set('score', score.toString());

    return this.http.post<Rank>(`${environment.leaderboardServiceUrl}/rank/${userId}/score`, {}, { params });
  }

  updateBestRefactoringScore(userId: number, exerciseId: string, score: number): Observable<Rank> {
    const params = new HttpParams()
      .set('exerciseId', exerciseId)
      .set('score', score.toString());

    return this.http.post<Rank>(`${environment.leaderboardServiceUrl}/rank/${userId}/refactoring`, {}, { params });
  }


  getUserRank(userId: number): Observable<UserRanking> {
    return this.http.get<UserRanking>(`${environment.leaderboardServiceUrl}/rank/${userId}/ranking`);
  }

}
