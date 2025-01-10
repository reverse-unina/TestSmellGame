import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { AssignmentsService } from "../../../services/assignments/assignments.service";
import { UserService } from "../../../services/user/user.service";
import { User } from '../../../model/user/user.model';
import { AssignmentConfiguration, Student } from '../../../model/assignment/assignment.model';
import {CheckGameExerciseConfig} from "../../../model/exercise/ExerciseConfiguration.model";

@Component({
  selector: 'app-assignments-list',
  templateUrl: './assignments-list-route.component.html',
  styleUrls: ['./assignments-list-route.component.css']
})
export class AssignmentsListRoute implements OnInit {

  assignments: AssignmentConfiguration[] = [];
  currentUser!: User;
  serverError:string | undefined = undefined;

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
    this.assignmentsService.getAssignmentsForStudent(this.currentUser!.userName).subscribe({
      next: (response: AssignmentConfiguration[]) => {
        //this.waitingForServer = false;
        this.serverError = undefined;
        this.assignments = response;
      },
      error: (err) => {
        //this.waitingForServer = false;
        this.serverError = err.error.message || 'An unexpected error occurred';
      }
    });
  }

  filterAssignmentsByGameType(gameType: string): AssignmentConfiguration[] {
    return this.assignments.filter(assignment => assignment.gameType === gameType);
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


