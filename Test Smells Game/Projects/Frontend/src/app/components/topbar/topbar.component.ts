import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {Subscription} from "rxjs";
import {UserService} from "../../services/user/user.service";
import {User} from "../../model/user/user.model";

@Component({
  selector: 'app-topbar',
  templateUrl: './topbar.component.html',
  styleUrls: ['./topbar.component.css']
})
export class TopbarComponent implements OnInit, OnDestroy {
  isAuthenticated = false;
  currentUser: User | null = null;

  private userSub: Subscription | undefined;
  constructor(private userService: UserService,
              private authService: AuthService) { }

  ngOnInit(): void {
    this.userSub = this.userService.user.subscribe(user => {
      this.isAuthenticated = !!user;
      this.currentUser = user;
    });
    console.log("Component created");
  }

  ngOnDestroy(): void {
    if (this.userSub) {
      this.userSub.unsubscribe();
    }
    console.log("Component destroyed");
  }

  onLogout() {
    this.authService.logout();
  }

  getStars(exp: number): string {
      if (exp < 5) {
        return '⭐';
      } else if (exp <= 15) {
        return '⭐⭐';
      } else {
        return '⭐⭐⭐';
      }
    }
}
