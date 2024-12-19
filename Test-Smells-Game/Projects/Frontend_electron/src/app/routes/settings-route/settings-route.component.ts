import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from "@angular/forms";
import {environment} from "../../../environments/environment.prod";
import {UserService} from "../../services/user/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TranslateService} from '@ngx-translate/core';
import {SettingsService} from "../../services/settings/settings.service";
import {ElectronService} from "ngx-electron";

@Component({
  selector: 'app-settings-route',
  templateUrl: './settings-route.component.html',
  styleUrls: ['./settings-route.component.css']
})
export class SettingsRouteComponent implements OnInit {

  environmentForm: any;
  selectedLanguage: string = "";
  exerciseType!: number;
  compileType!: number;

  isUserAuthenticated!: boolean;
  constructor(private fb: FormBuilder,
              private userService: UserService,
              private _snackBar: MatSnackBar,
              private settingsService: SettingsService,
              private _electronService: ElectronService,
              private zone: NgZone) {
      this._electronService.ipcRenderer.on('receiveDependenciesCheck', (event, data)=>{
      this.zone.run(()=>{
        if(data[0] == false)
          this._snackBar.open("To use Local Compile mode you need to install Maven", "Close", {
            duration: 3000
          });

        if(data[1] == false)
          this._snackBar.open("To use Local Compile mode you need to install Java", "Close", {
            duration: 3000
          });
        })
    })
  }

  switchLanguage(language: string) {
    this.settingsService.switchLanguage(language);
    this._snackBar.open('Settings saved', 'Close', {
      duration: 3000
    });
  }

  ngOnInit(): void {
    this.settingsService.initializeTranslationService();
    this.selectedLanguage = this.settingsService.getSelectedLanguage();
    this.settingsService.initializeEnvironmentUrls();
    this.fillEnvironmentForm();

    const exerciseRetrieval:string | null = this.settingsService.getSelectedExerciseRetrieval();
    if (exerciseRetrieval)
      this.exerciseType = parseInt(exerciseRetrieval);

    const compileMode:string | null = this.settingsService.getSelectedCompileMode();
    if (compileMode)
      this.compileType = parseInt(compileMode);

    this.isUserAuthenticated = this.userService.user.getValue() !== null;
  }

  fillEnvironmentForm() {
    this.environmentForm = this.fb.group({
      user_service: environment.userServiceUrl,
      compiler_service: environment.compilerServiceUrl,
      exercise_service: environment.exerciseServiceUrl,
      leaderboard_service: environment.leaderboardServiceUrl
    })
  }

  clickCompileCheckbox(compileType: number) {
    this.settingsService.checkDependencies();
    this.compileType = compileType;
  }

  clickExerciseCheckbox(number: number) {
    this.settingsService.checkDependencies();
    this.exerciseType = number;
  }

  submitEnvironmentForm(environmentForm: any) {
    this.settingsService.updateEnvironmentsUrl(environmentForm);

    this._snackBar.open('Settings saved', 'Close', {
      duration: 3000
    });
  }

  submitExerciseCheckboxes(checkboxForm: NgForm) {
    localStorage.setItem("exerciseRetrieval", this.exerciseType.toString());
    this._snackBar.open('Settings saved', 'Close', {
      duration: 3000
    });
  }

  submitCompileCheckboxes(checkboxForm: NgForm) {
    localStorage.setItem("compileMode", this.compileType.toString());
    this._snackBar.open('Settings saved', 'Close', {
      duration: 3000
    });
  }

  clearLocalStorage() {
    const confirmMessage = this.settingsService.getSelectedLanguage() === 'it' ? 'Sei sicuro di voler eliminare tutti i dati presenti nel Local Storage?' : 'Are you sure you want to clear all data in Local Storage?';
    const confirmed = window.confirm(confirmMessage);

    if (confirmed) {
      const savedLanguage:string | null = localStorage.getItem('selectedLanguage');
      const userServiceUrl:string | null = localStorage.getItem("userServiceUrl");
      const leaderboardServiceUrl:string | null = localStorage.getItem("leaderboardServiceUrl")
      const exerciseServiceUrl:string | null = localStorage.getItem("exerciseServiceUrl");
      const compilerServiceUrl:string | null = localStorage.getItem("compilerServiceUrl");

      localStorage.clear();

      if (savedLanguage)
        localStorage.setItem('selectedLanguage', savedLanguage);
      if (userServiceUrl)
        environment.userServiceUrl = userServiceUrl;
      if (leaderboardServiceUrl)
        environment.leaderboardServiceUrl = leaderboardServiceUrl;
      if (exerciseServiceUrl)
        environment.exerciseServiceUrl = exerciseServiceUrl;
      if (compilerServiceUrl)
        environment.compilerServiceUrl = compilerServiceUrl;

      this._snackBar.open('Local Storage cleared', 'Close', {
             duration: 3000
        });
      }
    }

  protected readonly localStorage = localStorage;
}
