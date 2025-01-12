import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {AssignmentConfiguration, Student} from '../../model/assignment/assignment.model';
import {environment} from "../../../environments/environment.prod";

@Injectable({
  providedIn: 'root'
})
export class AssignmentsService {
  constructor(private http: HttpClient) { }

  private getHttpHeaders(): HttpHeaders {
    return new HttpHeaders({
      'ngrok-skip-browser-warning': 'true'
    });
  }

  getAssignments(): Observable<AssignmentConfiguration[]> {
    return this.http.get<AssignmentConfiguration[]>(environment.exerciseServiceUrl + '/assignments/', { headers: this.getHttpHeaders() });
  }

  getAssignmentByName(name: string): Observable<AssignmentConfiguration | undefined> {
    return this.getAssignments().pipe(
      map(assignments => assignments.find(assignment => assignment.assignmentId === name)));
  }

  getAssignmentsForStudent(studentName: string): Observable<AssignmentConfiguration[]> {
     return this.getAssignments().pipe(
       map(assignments => assignments.filter(assignment => assignment.students.some(student => student.name === studentName)))
     );
   }

  getCurrentStudentForAssignment(assignmentName: string, studentName: string): Observable<Student | undefined> {
     return this.getAssignmentByName(assignmentName).pipe(
       map(assignment => {
         if (assignment) {
           return assignment.students.find(student => student.name === studentName);
         } else {
           return undefined;
         }
       })
     );
  }

  submitCheckSmellAssignment(assignmentName: string, studentName: string, exerciseId: string, results: string) {
    const formData = new FormData();
    formData.append('assignmentId', assignmentName);
    formData.append('studentName', studentName);
    formData.append('exerciseId', exerciseId);
    formData.append('results', new Blob([results], { type: 'text/plain' }), `${studentName}_results.txt`);

    return this.http.post(environment.exerciseServiceUrl + '/assignments/submit/checksmell', formData, { headers: this.getHttpHeaders() });
  }

  submitRefactoringAssignment(assignmentName: string, studentName: string, exerciseId: string, productionCode: string, testCode: string, shellCode: string, results: string): Observable<any> {
     const formData = new FormData();
     formData.append('assignmentId', assignmentName);
     formData.append('studentName', studentName);
     formData.append('exerciseId', exerciseId);
     formData.append('productionCode', new Blob([productionCode], { type: 'text/plain' }), `${studentName}_ClassCode.java`);
     formData.append('testCode', new Blob([testCode], { type: 'text/plain' }), `${studentName}_TestCode.java`);
     formData.append('shellCode', new Blob([shellCode], { type: 'text/plain' }), `${studentName}_ShellCode.java`);
     formData.append('results', new Blob([results], { type: 'text/plain' }), `${studentName}_results.txt`);

     return this.http.post(environment.exerciseServiceUrl + '/assignments/submit/refactoring', formData, { headers: this.getHttpHeaders() });
  }

}


