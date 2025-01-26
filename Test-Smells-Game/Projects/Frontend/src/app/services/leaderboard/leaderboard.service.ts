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
    return this.http.post(environment.leaderboardServiceUrl+'/leaderboard/refactoring', body, HTTPOptions)
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
  createNewScore(userName: string): Observable<Score> {
    return this.http.post<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}`, {});
  }

  getScore(userName: string): Observable<Score> {
    return this.http.get<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}`);
  }

  updateMissionsScore(userName: string, score: number): Observable<Score> {
    const params = new HttpParams()
      .set('score', score.toString());

    return this.http.put<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}/missions`, {}, { params });
  }

  updateBestCheckSmellScore(userName: string, exerciseId: string, score: number): Observable<Score> {
    const params = new HttpParams()
      .set('exerciseId', exerciseId)
      .set('score', score.toString());

    return this.http.put<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}/checksmell`, {}, { params });
  }

  updateBestRefactoringScore(userName: string, exerciseId: string, score: number): Observable<Score> {
    const params = new HttpParams()
      .set('exerciseId', exerciseId)
      .set('score', score.toString());

    return this.http.put<Score>(`${environment.leaderboardServiceUrl}/rank/${userName}/refactoring`, {}, { params });
  }

  getUserRank(userName: string): Observable<UserRanking> {
    return this.http.get<UserRanking>(`${environment.leaderboardServiceUrl}/rank/${userName}/ranking`);
  }

  getGameModePodium(podiumDimension: number): Observable<PodiumRanking> {
    const params = new HttpParams()
      .set('podiumDimension', podiumDimension);

    return this.http.get<PodiumRanking>(`${environment.leaderboardServiceUrl}/rank/podium/gamemode`, { params });
  }

  getRefactoringExercisePodium(podiumDimension: number): Observable<PodiumRanking> {
    const params = new HttpParams()
      .set('podiumDimension', podiumDimension);

    return this.http.get<PodiumRanking>(`${environment.leaderboardServiceUrl}/rank/podium/refactoring`, { params });
  }

}
