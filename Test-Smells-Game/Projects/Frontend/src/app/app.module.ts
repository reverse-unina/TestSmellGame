import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './components/auth/auth.component';
import { TopbarComponent } from './components/topbar/topbar.component';
import { ReactiveFormsModule } from "@angular/forms";
import { HttpClient, HttpClientModule } from "@angular/common/http";
import { HomeComponent } from './components/home/home.component';
import { AuthService } from "./services/auth/auth.service";
import { CommonModule } from "@angular/common";
import { CodeEditorComponent } from './components/codeeditor/code-editor.component';
import {
  RefactoringGameCoreRouteComponent
} from './routes/refactoring-game/refactoring-game-core/refactoring-game-core-route.component';
import { ProfileRouteComponent } from './routes/profile-route/profile-route.component';
import { CodemirrorModule } from '@ctrl/ngx-codemirror';
import {
  RefactoringGameExListRouteComponent
} from './routes/refactoring-game/refactoring-game-exercise-list/refactoring-game-ex-list-route.component';
import { CheckGameExListRoute } from './routes/check-smell-game/check-smell-exercise-list/check-game-ex-list-route';
import { CheckGameCoreRouteComponent } from './routes/check-smell-game/check-game-core/check-game-core-route.component';
import { TestGameComponent } from './routes/test-game/test-game.component';
import { AssignmentsListRoute } from './routes/assignments-route/assignments-list/assignments-list-route';
import { AssignmentsCoreRouteComponent } from './routes/assignments-route/assignments-core/assignments-core-route.component';
import { SolutionComponent } from './components/solution/solution.component';
import { LeaderboardRouteComponent } from './routes/leaderboard-route/leaderboard-route.component';
import { LoaderComponent } from './components/loader/loader.component';
import { SettingsRouteComponent } from './routes/settings-route/settings-route.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeRouteComponent } from './routes/home-route/home-route.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatExpansionModule } from "@angular/material/expansion";
import { MatListModule } from "@angular/material/list";
import { MatIconModule } from "@angular/material/icon";
import { MatTabsModule } from "@angular/material/tabs";
import { MatCardModule } from "@angular/material/card";
import { MatProgressBarModule } from "@angular/material/progress-bar";
import { LogtodoComponent } from './components/logs/logtodo/logtodo.component';
import { LogknownissuesComponent } from './components/logs/logknownissues/logknownissues.component';
import { LogviewerComponent } from './components/logs/logviewer/logviewer.component';
import { LogelementComponent } from './components/logs/logviewer/logelement/logelement.component';
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatCheckboxModule } from '@angular/material/checkbox';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { SuccessAlertComponent } from './components/alert/success-alert/success-alert.component';
import { FailAlertComponent } from './components/alert/fail-alert/fail-alert.component';
import { AchievementAlertComponent } from './components/alert/achivement-alert/achievement-alert.component';
import { RefactoringScoreComponent } from './components/refactoring/refactoring-score/refactoring-score.component';
import { RefactoringSmellListComponent } from './components/refactoring/refactoring-smell-list/refactoring-smell-list.component';
import { RefactoringCompilationResultsComponent } from './components/refactoring/refactoring-compilation-results/refactoring-compilation-results.component';
import { RefactoringEditorCardComponent } from './components/refactoring/refactoring-editor-card/refactoring-editor-card.component';
import { CheckSmellAnswerListComponent } from './components/check-smell/check-smell-answer-list/check-smell-answer-list.component';
import { CheckSmellQuestionComponent } from './components/check-smell/check-smell-question/check-smell-question.component';
import { LearningPageRouteComponent } from './routes/learning-page-route/learning-page-route.component';
import { MissionsListRouteComponent } from './routes/missions-route/missions-list-route/missions-list-route.component';
import { MissionsCoreRouteComponent } from './routes/missions-route/missions-core-route/missions-core-route.component';
import { MissionProgressComponent } from './components/mission-progress/mission-progress.component';
import { ErrorComponent } from './components/error/error.component';
import { PodiumComponent } from './components/podium/podium.component';
import { RankComponent } from './components/rank/rank.component';
import { RouterModule } from "@angular/router";
import { TestSummaryComponent } from './routes/test-summary/test-summary.component';
import { TestHistoryComponent } from './routes/test-history/test-history.component';
import { GraficoComponent } from './components/graphic/graphic.component';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    TopbarComponent,
    HomeComponent,
    CodeEditorComponent,
    RefactoringGameCoreRouteComponent,
    RefactoringGameExListRouteComponent,
    CheckGameExListRoute,
    CheckGameCoreRouteComponent,
    TestGameComponent,
    SolutionComponent,
    LeaderboardRouteComponent,
    LoaderComponent,
    SettingsRouteComponent,
    HomeRouteComponent,
    ProfileRouteComponent,
    LogtodoComponent,
    LogknownissuesComponent,
    LogviewerComponent,
    LogelementComponent,
    AssignmentsListRoute,
    AssignmentsCoreRouteComponent,
    SuccessAlertComponent,
    FailAlertComponent,
    AchievementAlertComponent,
    RefactoringScoreComponent,
    RefactoringSmellListComponent,
    RefactoringCompilationResultsComponent,
    RefactoringEditorCardComponent,
    CheckSmellAnswerListComponent,
    CheckSmellQuestionComponent,
    LearningPageRouteComponent,
    MissionsListRouteComponent,
    MissionsCoreRouteComponent,
    MissionProgressComponent,
    ErrorComponent,
    PodiumComponent,
    RankComponent,
    TestSummaryComponent,
    TestHistoryComponent,
    GraficoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    CodemirrorModule,
    BrowserAnimationsModule,
    MatSnackBarModule,
    MatExpansionModule,
    MatListModule,
    MatIconModule,
    MatTabsModule,
    MatCardModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatCheckboxModule,
    TranslateModule.forRoot({
      defaultLanguage: 'en',
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
    RouterModule
  ],  
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
