import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment.prod";
import {CheckSmellStatistics, RefactoringSolution} from "../../model/solution/solution";
import {Exercise} from "../../model/exercise/refactor-exercise.model";
import {UserService} from "../user/user.service";
import {RefactoringGameExerciseConfiguration} from "../../model/exercise/ExerciseConfiguration.model";
import {PodiumRanking, Score, UserRanking} from "../../model/rank/score";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {

  constructor(private http: HttpClient, private userService: UserService) { }

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true'
    });
  }

  getRefactoringSolutionByExerciseId(exercise: string){
    return this.http.get<RefactoringSolution[]>(environment.leaderboardServiceUrl + '/leaderboard/refactoring/' + exercise);
  }

  getCheckSmellSolutionsByExerciseName(exercise: string){
    return this.http.get<CheckSmellStatistics[]>(environment.leaderboardServiceUrl + '/leaderboard/checksmell/' + exercise);
  }

  saveRefactoringSolution(exercise: Exercise,
                          exerciseConfiguration: RefactoringGameExerciseConfiguration,
                          score: number,
                          refactoringResult: boolean,
                          originalCoverage: number,
                          refactoredCoverage:number,
                          smells: Object){

    const body = {
      "exerciseId": exerciseConfiguration.exerciseId,
      "playerName": this.userService.user.getValue().userName,
      "refactoredCode": exercise.refactoredTestCode,
      "score": score,
      "refactoringResult": refactoringResult,
      "originalCoverage": originalCoverage,
      "refactoredCoverage": refactoredCoverage,
      "smells": smells
    };

    return this.http.post(environment.leaderboardServiceUrl + +'/leaderboard/refactoring', body, { headers: this.getHttpHeaders() });
  }

  saveCheckSmellSolution(exerciseId: string,
                         score: number,
                         correctAnswers: number,
                         wrongAnswers: number,
                         missedAnswers: number
  ){
    let HTTPOptions:Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
    }
    const body = {
      "exerciseId": exerciseId,
      "playerName": this.userService.user.getValue().userName,
      "score": score,
      "missedAnswers": missedAnswers,
      "correctAnswers": correctAnswers,
      "wrongAnswers": wrongAnswers
    }

    console.log(body);
    return this.http.post(environment.leaderboardServiceUrl+'/leaderboard/checksmell', body, HTTPOptions)
  }

  postComment(comment: string, solutionId: number, commentAuthor: string) {
    const body = {
      "commentAuthor": commentAuthor,
      "commentText": comment,
      "solutionId": solutionId
    };

    return this.http.post(environment.leaderboardServiceUrl + "/leaderboard/solution/postComment", body, { headers: this.getHttpHeaders() });
  }

  voteSolution(solutionId: number, userId: number, vote: string) {
    const body = {
      "solutionId": solutionId,
      "userId": userId,
      "voteType": vote
    };

    return this.http.post(environment.leaderboardServiceUrl + '/leaderboard/solution/' + solutionId, body, { headers: this.getHttpHeaders() });
  }

  getVoteForUser(solutionId: number, userId: number) {
    return this.http.get(environment.leaderboardServiceUrl + '/leaderboard/solution/' + solutionId + '/' + userId, { headers: this.getHttpHeaders() });
  }


  /* Rank methods */
  createNewScore(userName: string): Observable<Score> {
    return this.http.post<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}`, {}, { headers: this.getHttpHeaders() });
  }

  getScore(userName: string): Observable<Score> {
    return this.http.get<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}`, { headers: this.getHttpHeaders() });
  }

  updateMissionsScore(userName: string, score: number): Observable<Score> {
    const params = new HttpParams()
      .set('score', score.toString());

    return this.http.put<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}/missions`, {}, { params, headers: this.getHttpHeaders() });
  }

  updateBestCheckSmellScore(userName: string, exerciseId: string, score: number): Observable<Score> {
    const params = new HttpParams()
      .set('exerciseId', exerciseId)
      .set('score', score.toString());

    return this.http.put<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}/checksmell`, {}, { params, headers: this.getHttpHeaders() });
  }

  updateBestRefactoringScore(userName: string, exerciseId: string, score: number): Observable<Score> {
    const params = new HttpParams()
      .set('exerciseId', exerciseId)
      .set('score', score.toString());

    return this.http.put<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}/refactoring`, {}, { params, headers: this.getHttpHeaders() });
  }

  getUserRank(userName: string): Observable<UserRanking> {
    return this.http.get<UserRanking>(`${environment.leaderboardServiceUrl}/rank/${userName}/ranking`, { headers: this.getHttpHeaders() });
  }

  getGameModePodium(podiumDimension: number): Observable<PodiumRanking> {
    const params = new HttpParams()
      .set('podiumDimension', podiumDimension);

    return this.http.get<PodiumRanking>(`${environment.leaderboardServiceUrl}/rank/podium/gamemode`, { params, headers: this.getHttpHeaders() });
  }

  getRefactoringExercisePodium(podiumDimension: number): Observable<PodiumRanking> {
    const params = new HttpParams()
      .set('podiumDimension', podiumDimension);

    return this.http.get<PodiumRanking>(`${environment.leaderboardServiceUrl}/rank/podium/refactoring`, { params, headers: this.getHttpHeaders() });
  }

}
