import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user/user.service';
import { User } from '../../model/user/user.model';

@Component({
  selector: 'app-profile-route',
  templateUrl: './profile-route.component.html',
  styleUrls: ['./profile-route.component.css']
})
export class ProfileRouteComponent implements OnInit {
  user!: User;
  userLevel!: string;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => {
      this.user = user;
      this.setUserLevel();
    });
  }

  private setUserLevel(): void {
    if (this.user.exp < 5) {
      this.userLevel = '⭐';
    } else if (this.user.exp >= 5 && this.user.exp < 15) {
      this.userLevel = '⭐⭐';
    } else {
      this.userLevel = '⭐⭐⭐';
    }
  }
}
