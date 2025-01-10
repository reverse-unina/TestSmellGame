export interface Student {
  name: string;
  exerciseId: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
  submitted: boolean;
}

export interface AssignmentConfiguration {
  assignmentId: string;
  students: Student[];
  type: string;
  gameType: string;
}
