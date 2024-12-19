import {Component, OnInit} from '@angular/core';
import {AuthService} from "./services/auth/auth.service";
import {environment} from "../environments/environment";
import {ElectronService} from "ngx-electron";
import {SettingsService} from "./services/settings/settings.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(private authService: AuthService,
              private settingsService: SettingsService,
              private _electronService: ElectronService) { }

  ngOnInit(): void {
    console.log(environment.userServiceUrl);
    this.settingsService.initializeTranslationService();
    this.settingsService.initializeEnvironmentUrls();
    this.authService.autoLogin();
  }
  title = 'Tesi';

}
