import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {AuthService} from "../../services/auth/auth.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ProgressBarMode} from "@angular/material/progress-bar";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html'
})
export class AuthComponent {
  progressBarMode: ProgressBarMode = 'determinate';
  isLoginMode = true;
  requestKO: boolean = false;
  requestOK: boolean = false;
  message = "";
  constructor(private authService: AuthService,
              private router: Router,
              private _snackBar: MatSnackBar) {
  }


  onSwitchMode() {
    this.resetErrors();
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit(form: NgForm) {
    this.resetErrors();
    const email = form.value.email;
    const password = form.value.password;
    const username = form.value.username;
    this.startLoading();

    if (this.isLoginMode)
      this.submitLoginMode(email, password);
    else
      this.submitSignUpMode(email, username, password);
    form.reset();
  }

  handleLoginErrors(code: number) {
    this.requestKO = true;
    switch (code) {
      case 401:
        this.message = "Wrong email or password";
        break;
      default:
        this.message = "Server has a problem";
        break;
    }
  }

  handleSignUpErrors(code: number){
    this.requestKO = true;
    switch (code) {
      case 401:
        this.message = "Email not available";
        break;
      case 402:
        this.message = "Username not available";
        break;
      default:
        this.message = "Server has a problem";
        break;
    }
  }

  handleSignUpSuccess(code: number){
    this.requestOK = true;
    switch(code){
      case 200:
        this.message = "User created successfully";
        this.isLoginMode = !this.isLoginMode;
        break;
      default:
        this.message = "";
        break;
    }
  }

  resetErrors(){
    this.requestKO = false;
    this.requestOK = false;
  }

  private submitLoginMode(email: string, password: string) {
      this.authService.login(email, password).subscribe(response => {
        this.stopLoading();
        this.router.navigate(['/home']);
      }, error => {
        this.handleLoginErrors(error.error.error_code);
        this.stopLoading();
        this._snackBar.open(this.message, "Close", {
          duration: 3000
        });
      });
    }

  private submitSignUpMode(email: string, username: string, password: string) {
    this.authService.signup(email, username, password).subscribe(response => {
      this.stopLoading()
      response = JSON.parse(JSON.stringify(response))
      // @ts-ignore
      this.handleSignUpSuccess(response.status);
    }, error => {
      this.stopLoading()
      this.handleSignUpErrors(error.error.error_code);
    });
  }

  startLoading(){
    this.progressBarMode = 'query'
  }

  stopLoading() {
    this.progressBarMode = 'determinate'
  }

}
