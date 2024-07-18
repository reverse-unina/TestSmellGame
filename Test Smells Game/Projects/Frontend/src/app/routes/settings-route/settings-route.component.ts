import {Component, NgZone, OnInit} from '@angular/core';
import {FormBuilder, NgForm} from "@angular/forms";
import {environment} from "../../../environments/environment.prod";
import {UserService} from "../../services/user/user.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {TranslateService} from '@ngx-translate/core';

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
              private translate: TranslateService) { }

  switchLanguage(language: string) {
    this.translate.use(language);
    const message = this.translate.currentLang === 'it' ? 'Impostazioni salvate' : 'Settings saved';
    const close_message = this.translate.currentLang === 'it' ? 'Chiudi' : 'Close';
    this._snackBar.open(message, close_message, {
          duration: 3000
        });
  }

  initializeTranslationService() {
    if (!this.translate.getDefaultLang())
      this.translate.setDefaultLang('en');
  }

  ngOnInit(): void {
    this.initializeTranslationService();
    this.fillEnvironmentForm();
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
  submitEnvironmentForm(environmentForm: any) {
    environment.userServiceUrl = environmentForm.value.user_service
    environment.compilerServiceUrl = environmentForm.value.compiler_service
    environment.leaderboardServiceUrl = environmentForm.value.leaderboard_service
    environment.exerciseServiceUrl = environmentForm.value.exercise_service

    const message = this.translate.currentLang === 'it' ? 'Impostazioni salvate' : 'Settings saved';
    const close_message = this.translate.currentLang === 'it' ? 'Chiudi' : 'Close';
    this._snackBar.open(message, close_message, {
      duration: 3000
    });
  }

  submitCompileCheckboxes(checkboxForm: NgForm) {

    const message = this.translate.currentLang === 'it' ? 'Impostazioni salvate' : 'Settings saved';
    const close_message = this.translate.currentLang === 'it' ? 'Chiudi' : 'Close';
    this._snackBar.open(message, close_message, {
        duration: 3000
    });
  }

  clearLocalStorage() {
    const confirmMessage = this.translate.currentLang === 'it' ? 'Sei sicuro di voler eliminare tutti i dati presenti nel Local Storage?' : 'Are you sure you want to clear all data in Local Storage?';
    const confirmed = window.confirm(confirmMessage);

    if (confirmed) {
      localStorage.clear();
        const message = this.translate.currentLang === 'it' ? 'Local Storage svuotato' : 'Local Storage cleared';
        const close_message = this.translate.currentLang === 'en' ? 'Close' : 'Chiudi';
        this._snackBar.open(message, close_message, {
             duration: 3000
        });
      }
    }
}
