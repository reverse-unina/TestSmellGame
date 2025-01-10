import {Component, OnInit} from '@angular/core';
import {AuthService} from "./services/auth/auth.service";
import {environment} from "../environments/environment";
import {SettingsService} from "./services/settings/settings.service";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Tesi';

  constructor(private authService: AuthService,
              private settingsService: SettingsService,
              private router: Router) { }

  ngOnInit(): void {
    console.log(environment.userServiceUrl);
    this.settingsService.initializeTranslationService();
    this.settingsService.initializeEnvironmentUrls();
    this.authService.autoLogin();
  }

}
