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
  loadingError = false;

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
        this.loadingError = false;
      },
      error => {
        console.error('Errore durante il recupero degli esperimenti:', error);
        this.loadingError = true;
      }
    );
  }

  isAssignmentActive(assignmentDate: string, startTime: string, endTime: string): boolean {
    const assignmentDateObj = new Date(assignmentDate);
    const [startHour, startMinute] = startTime.split(':').map(Number);
    const [endHour, endMinute] = endTime.split(':').map(Number);

    const startDateTime = new Date(assignmentDateObj);
    startDateTime.setHours(startHour, startMinute, 0, 0);

    const endDateTime = new Date(assignmentDateObj);
    endDateTime.setHours(endHour, endMinute, 0, 0);

    const currentTime = new Date();

    return currentTime >= startDateTime && currentTime <= endDateTime;
  }

  checkAssignmentPresence(): number {
    let count = 0;
    this.assignments.forEach(assignment => {
      if (assignment.students.some(student => student.name === this.currentUser?.userName)) {
        count++;
      }
    });
    return count;
  }
}


