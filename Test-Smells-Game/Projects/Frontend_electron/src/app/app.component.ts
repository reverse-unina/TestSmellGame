import {Component, OnInit} from '@angular/core';
import {AuthService} from "./services/auth/auth.service";
import {environment} from "../environments/environment";
import {ElectronService} from "ngx-electron";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(private authService: AuthService, private _electronService: ElectronService) { }

  ngOnInit(): void {
    console.log(environment.userServiceUrl);
    this.authService.autoLogin();
  }
  title = 'Tesi';

}
