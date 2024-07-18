import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {MatSnackBar} from '@angular/material/snack-bar';
import {User} from "../../model/user/user.model";
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = environment.userServiceUrl + '/users/';

  user = new BehaviorSubject<User>(new User());
  constructor(private snackBar: MatSnackBar, private http: HttpClient) { }

  getCurrentUser(): Observable<User> {
      return this.user.asObservable();
    }

  getUserExp(): number {
      return this.user.value.exp;
    }

   increaseUserExp(): void {
      const currentUser = this.user.value;
      currentUser.exp += 1;
      this.user.next(currentUser);

      switch (currentUser.exp) {
        case 5:
          this.showSnackBar('Congratulations! You have reached level 2');
          break;
        case 15:
          this.showSnackBar('Congratulations! You have reached level 3');
          break;
        case 10:
        case 20:
        case 30:
          this.showSnackBar('Congratulations! You have unlocked a new badge, view it on your profile page');
          break;
        default:
          break;
      }
      this.http.post(`${this.baseUrl}updateExp`, currentUser).subscribe(
            response => {
              console.log('User updated successfully', response);
            },
            error => {
              console.error('Error occurred during updating user', error);
            }
          );
    }

    updateUser(newUser: User): void {
      this.user.next(newUser);
    }

    private showSnackBar(message: string): void {
      this.snackBar.open(message, 'Close', {
        duration: 3000,
        verticalPosition: 'bottom',
        horizontalPosition: 'center'
      });
    }
  }
