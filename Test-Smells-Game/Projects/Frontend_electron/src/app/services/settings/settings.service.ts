import {Injectable} from '@angular/core';
import {ElectronService} from "ngx-electron";
import {TranslateService} from "@ngx-translate/core";
import {environment} from "../../../environments/environment.prod";
import {FormBuilder} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class SettingsService {
  private selectedLanguage:string = ""

  constructor(private _electronService: ElectronService,
              private fb: FormBuilder,
              private translate: TranslateService) { }

  checkDependencies(){
    this._electronService.ipcRenderer.send("checkDependencies")
  }

  initializeTranslationService():void {
    const savedLanguage = localStorage.getItem('selectedLanguage');

    if (savedLanguage) {
      this.translate.setDefaultLang(savedLanguage);
      this.selectedLanguage = savedLanguage;
    } else {
      this.translate.setDefaultLang('en');
      localStorage.setItem('selectedLanguage', 'en');
      this.selectedLanguage = 'en';
    }
  }

  initializeEnvironmentUrls():void{
    const userServiceUrl:string | null = localStorage.getItem("userServiceUrl");
    if (userServiceUrl)
      environment.userServiceUrl = userServiceUrl;

    const leaderboardServiceUrl:string | null = localStorage.getItem("leaderboardServiceUrl");
    if (leaderboardServiceUrl)
      environment.leaderboardServiceUrl = leaderboardServiceUrl;

    const exerciseServiceUrl:string | null = localStorage.getItem("exerciseServiceUrl");
    if (exerciseServiceUrl)
      environment.exerciseServiceUrl = exerciseServiceUrl;

    const compilerServiceUrl:string | null = localStorage.getItem("compilerServiceUrl");
    if (compilerServiceUrl)
      environment.compilerServiceUrl = compilerServiceUrl;
  }

  getSelectedExerciseRetrieval():string | null {
    return localStorage.getItem("exerciseRetrieval");
  }

  getSelectedCompileMode():string | null {
    return localStorage.getItem("compileMode");
  }

  getSelectedLanguage():string {
    return this.selectedLanguage;
  }

  switchLanguage(language:string){
    this.selectedLanguage = language;
    this.translate.use(language);
    localStorage.setItem('selectedLanguage', language);
  }

  updateEnvironmentsUrl(environmentForm:any){
    localStorage.setItem("userServiceUrl", environmentForm.value.user_service);
    localStorage.setItem("compilerServiceUrl", environmentForm.value.compiler_service);
    localStorage.setItem("leaderboardServiceUrl", environmentForm.value.leaderboard_service);
    localStorage.setItem("exerciseServiceUrl", environmentForm.value.exercise_service);

    environment.userServiceUrl = environmentForm.value.user_service
    environment.compilerServiceUrl = environmentForm.value.compiler_service
    environment.leaderboardServiceUrl = environmentForm.value.leaderboard_service
    environment.exerciseServiceUrl = environmentForm.value.exercise_service
  }
}
