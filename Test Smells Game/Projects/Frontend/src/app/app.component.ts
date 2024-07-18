import {Component, OnInit} from '@angular/core';
import {AuthService} from "./services/auth/auth.service";
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    console.log(environment.userServiceUrl);
    this.authService.autoLogin();
  }
  title = 'Tesi';

}
