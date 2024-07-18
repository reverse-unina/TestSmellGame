import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import { Assignment, Student } from '../../model/assignment/assignment.model';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { environment } from "../../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})

export class AssignmentsService {
  private baseUrl = environment.exerciseServiceUrl + '/assignments/';

  constructor(private http: HttpClient) { }

  getAssignments(): Observable<Assignment[]> {
    return this.http.get<Assignment[]>(this.baseUrl + 'get-assignments');
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

     submitAssignment(assignmentName: string, studentName: string, productionCode: string, testCode: string, shellCode: string, results: string): Observable<any> {
         const formData = new FormData();
         formData.append('assignmentName', assignmentName);
         formData.append('studentName', studentName);
         formData.append('productionCode', new Blob([productionCode], { type: 'text/plain' }), `${studentName}_ClassCode.java`);
         formData.append('testCode', new Blob([testCode], { type: 'text/plain' }), `${studentName}_TestCode.java`);
         formData.append('shellCode', new Blob([shellCode], { type: 'text/plain' }), `${studentName}_ShellCode.java`);
         formData.append('results', new Blob([results], { type: 'text/plain' }), `${studentName}_results.txt`);

         return this.http.post(this.baseUrl + 'submit-assignment', formData);
     }

}


