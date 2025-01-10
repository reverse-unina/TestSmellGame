import {MissionStatus} from "../missions/mission.model";

export class User {
  userId!: number;
  email!: string;
  userName!: string;
  firstName!: string;
  lastName!: string;
  token!: string;
  exp!: number;
  missionsStatus: MissionStatus[] = [];

  constructor() {}

/*constructor(userId: number, email: string, userName: string, firstName: string, lastName: string, token: string) {
    this.userId = userId;
    this.email = email;
    this.userName = userName;
    this.firstName = firstName;
    this.lastName = lastName;
    this.token = token;
  }*/
}


