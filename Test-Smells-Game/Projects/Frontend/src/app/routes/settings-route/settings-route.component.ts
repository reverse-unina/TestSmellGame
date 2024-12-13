import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from "@angular/forms";
import {environment} from "../../../environments/environment.prod";
import {UserService} from "../../services/user/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TranslateService} from '@ngx-translate/core';
import {SettingsService} from "../../services/settings/settings.service";

@Component({
  selector: 'app-settings-route',
  templateUrl: './settings-route.component.html',
  styleUrls: ['./settings-route.component.css']
})
export class SettingsRouteComponent implements OnInit {

  environmentForm: any;
  selectedLanguage: string = "";

  isUserAuthenticated!: boolean;
  constructor(private fb: FormBuilder,
              private userService: UserService,
              private _snackBar: MatSnackBar,
              private settingsService: SettingsService) { }

  ngOnInit(): void {
    this.settingsService.initializeTranslationService();
    this.selectedLanguage = this.settingsService.getSelectedLanguage();
    this.settingsService.initializeEnvironmentUrls();
    this.fillEnvironmentForm();

    this.isUserAuthenticated = this.userService.user.getValue() !== null;
  }

  switchLanguage(language: string) {
    this.settingsService.switchLanguage(language);
    const message = this.settingsService.getSelectedLanguage() === 'it' ? 'Impostazioni salvate' : 'Settings saved';
    const close_message = this.settingsService.getSelectedLanguage() === 'it' ? 'Chiudi' : 'Close';
    this._snackBar.open(message, close_message, {
      duration: 3000
    });
  }

  fillEnvironmentForm() {
    this.environmentForm = this.fb.group({
      user_service: environment.userServiceUrl,
      compiler_service: environment.compilerServiceUrl,
      exercise_service: environment.exerciseServiceUrl,
      leaderboard_service: environment.leaderboardServiceUrl
    })
  }

  submitEnvironmentForm(environmentForm: any) {
    this.settingsService.updateEnvironmentsUrl(environmentForm);
    const message = this.settingsService.getSelectedLanguage() === 'it' ? 'Impostazioni salvate' : 'Settings saved';
    const close_message = this.settingsService.getSelectedLanguage() === 'it' ? 'Chiudi' : 'Close';
    this._snackBar.open(message, close_message, {
      duration: 3000
    });
  }

  submitCompileCheckboxes(checkboxForm: NgForm) {
    const message = this.settingsService.getSelectedLanguage() === 'it' ? 'Impostazioni salvate' : 'Settings saved';
    const close_message = this.settingsService.getSelectedLanguage() === 'it' ? 'Chiudi' : 'Close';
    this._snackBar.open(message, close_message, {
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

      const message = this.settingsService.getSelectedLanguage() === 'it' ? 'Local Storage svuotato' : 'Local Storage cleared';
      const close_message = this.settingsService.getSelectedLanguage() === 'en' ? 'Close' : 'Chiudi';
      this._snackBar.open(message, close_message, {
           duration: 3000
      });
    }
  }
}
