import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { AssignmentsService } from "../../../services/assignments/assignments.service";
import { UserService } from "../../../services/user/user.service";
import { User } from '../../../model/user/user.model';
import { Assignment, Student } from '../../../model/assignment/assignment.model';

@Component({
  selector: 'app-assignments-list',
  templateUrl: './assignments-list-route.component.html',
  styleUrls: ['./assignments-list-route.component.css']
})
export class AssignmentsListRoute implements OnInit {

  assignments: Assignment[] = [];
  currentUser!: User;
  errorMessage:string | undefined = undefined;
  errorParts:string[] = [];

  constructor(private _router: Router,
              private assignmentsService: AssignmentsService,
              private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
      if (this.currentUser) {
        this.loadAssignments();
      }
    });
  }

  loadAssignments() {
    this.assignmentsService.getAssignmentsForStudent(this.currentUser!.userName).subscribe(
      assignments => {
        this.assignments = assignments;
        this.errorMessage = undefined;
        this.errorParts = [];
      },
      error => {
        this.errorMessage = error.error;
        console.error(this.errorMessage);
        this.refactorErrorMessage(this.errorMessage);
      }
    );
  }

  filterAssignmentsByGameType(gameType: string): Assignment[] {
    return this.assignments.filter(assignment => assignment.gameType === gameType);
  }

  refactorErrorMessage(error: string | undefined) : void {
    if (error === undefined)
      return;

    const matchRegexMissingField = error.match(/Missing required property "(.*?)" in file (.*)/);
    const matchRegexUnrecognizedField = error.match(/Unrecognized field "(.*?)" not marked as ignorable found in file (.*)/);
    const matchRegexReadingFile = error.match(/Error reading assignment file (.*)/);

    if (matchRegexMissingField) {
      this.errorParts = [
        matchRegexMissingField[0].split(/ "(.*?)"/)[0],
        `"${matchRegexMissingField[1]}"`,
        "in file",
        matchRegexMissingField[2]
      ];
      console.log("Error Parts: ", this.errorParts);
    } else if (matchRegexUnrecognizedField) {
      this.errorParts = [
        matchRegexUnrecognizedField[0].split(/ "(.*?)"/)[0],
        `"${matchRegexUnrecognizedField[1]}"`,
        "not marked as ignorable found in file",
        matchRegexUnrecognizedField[2]
      ];
    } else if (matchRegexReadingFile) {
      this.errorParts = [
        matchRegexReadingFile[0].split(/ "(.*?)"/)[0],
        matchRegexReadingFile[1]
      ];
    } else {
      this.errorParts[0] = error;
    }
  }

  isAssignmentActive(assignmentStartDate: string, startTime: string, assignmentEndDate: string, endTime: string): boolean {
    const [startHour, startMinute] = startTime.split(':').map(Number);
    const [endHour, endMinute] = endTime.split(':').map(Number);

    const startDateTime = new Date(assignmentStartDate);
    startDateTime.setHours(startHour, startMinute, 0, 0);

    const endDateTime = new Date(assignmentEndDate);
    endDateTime.setHours(endHour, endMinute, 0, 0);

    const currentTime = new Date();

    return currentTime >= startDateTime && currentTime <= endDateTime;
  }

  hasStudentAssignments(gameType: string): boolean{
    return this.assignments.filter(assignment => assignment.gameType === gameType).length > 0;
  }

}


