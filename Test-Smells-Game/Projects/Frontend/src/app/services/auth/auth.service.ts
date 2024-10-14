import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {User} from "../../model/user/user.model";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment.prod";
import {UserService} from "../user/user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
              private router: Router,
              private userService: UserService) {
  }

  signup(email: string, username: string, password: string) {
    return this.http.post(environment.userServiceUrl+'/users/', {
      email: email,
      password: password,
      username: username
    }).pipe(tap(response => {
      this.router.navigate(['/auth']);
    }))
  }

  login(email: string, password: string) {
    return this.http.post(environment.userServiceUrl+'/users/authenticate/', {
      email: email,
      password: password
    }).pipe(tap(response => {
      // @ts-ignore
      this.handleAuthentication(email, response.token);
    }))
  }

  handleAuthentication(email: string, token: string){
    const storedEmail = localStorage.getItem("email");
    if (storedEmail && storedEmail !== email)
      localStorage.clear();

    this.http.post<User>(environment.userServiceUrl + '/users/userData', {
      email: email,
      token: token
    }).subscribe(user => {
      this.userService.user.next(user);
      localStorage.setItem("email",email);
      localStorage.setItem("token",token);
      this.router.navigate(['/home']);
    },error => {
      this.logout();
    })
  }

  autoLogin(){
    // @ts-ignore
    this.userService.user.next(null);
    const email = localStorage.getItem("email");
    const token = localStorage.getItem("token");
    if(email == null || token == null)
      this.logout();
    else
      this.handleAuthentication(email, token);
  }

  logout() {
    // @ts-ignore
    this.userService.user.next(null);
    this.router.navigate(['/auth']);
  }
}
