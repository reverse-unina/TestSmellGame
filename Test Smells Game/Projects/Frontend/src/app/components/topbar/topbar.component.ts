import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {Subscription} from "rxjs";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit, OnDestroy {
  isAuthenticated = false;
  private userSub: Subscription | undefined;
  constructor(private userService: UserService,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user =>{
      this.isAuthenticated = !!user;
    });
    console.log("Creato");
  }

  ngOnDestroy(): void {
    // @ts-ignore
    this.userSub.unsubscribe();
    console.log("Distrutto");
  }

  onLogout() {
    this.authService.logout();
  }
}
