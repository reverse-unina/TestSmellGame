import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {User} from "../../model/user/user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  user = new BehaviorSubject<User>(new User());
  constructor() { }

  getCurrentUser(): Observable<User> {
      return this.user.asObservable();
    }


}
