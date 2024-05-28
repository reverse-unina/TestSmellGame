import {Component, NgZone, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, NgForm} from "@angular/forms";
import {environment} from "../../../../environments/environment.prod";
import {AssignmentsService} from "../../../services/assignments/assignments.service";
import {Assignment} from '../../../model/assignment/assignment.model';
import {UserService} from "../../../services/user/user.service"
import {User} from '../../../model/user/user.model'
import {Student} from '../../../model/assignment/assignment.model';

@Component({
  selector: 'app-assignments-list',
  templateUrl: './assignments-list-route.component.html',
  styleUrls: ['./assignments-list-route.component.css']
})
export class AssignmentsListRoute implements OnInit {

  assignments: Assignment[] = [];
  currentUser!: User;
  currentStudent!: Student;

  constructor(private _router: Router,
              private assignmentsService: AssignmentsService,
              private userService: UserService
  ) { }

  ngOnInit(): void {
    this.assignmentsService.getAssignments().subscribe(assignments => {
      this.assignments = assignments;
    });
    this.userService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
    });
    if (this.currentUser)
      this.loadAssignments();
  }

  loadAssignments() {
      this.assignmentsService.getAssignmentsForStudent(this.currentUser!.userName).subscribe(assignments => {
        this.assignments = assignments;
      });
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

}
