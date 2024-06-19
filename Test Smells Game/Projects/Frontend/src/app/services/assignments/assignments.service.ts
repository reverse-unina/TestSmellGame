import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import { Assignment, Student } from '../../model/assignment/assignment.model';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AssignmentsService {
  private baseUrl = 'assets/assignments/';

  constructor(private http: HttpClient) { }

  getAssignments(): Observable<Assignment[]> {
          const assignmentFiles = ['esperimento1.json', 'esperimento2.json'];
          const requests = assignmentFiles.map(file => this.http.get<any>(`${this.baseUrl}${file}`));
          return forkJoin(requests);
  }

  getAssignmentByName(name: string): Observable<Assignment | undefined> {
       return this.getAssignments().pipe(
         map(assignments => assignments.find(assignment => assignment.nome === name))
       );
     }

   getAssignmentsForStudent(studentName: string): Observable<Assignment[]> {
         return this.getAssignments().pipe(
           map(assignments => assignments.filter(assignment =>
             assignment.studenti.some(student => student.nome === studentName)
           ))
         );
       }

     getCurrentStudentForAssignment(assignmentName: string, studentName: string): Observable<Student | undefined> {
         return this.getAssignmentByName(assignmentName).pipe(
           map(assignment => {
             if (assignment) {
               return assignment.studenti.find(student => student.nome === studentName);
             } else {
               return undefined;
             }
           })
         );
       }
}

