export interface Student {
  name: string;
  exercise: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
  submitted: boolean;
}

export interface AssignmentConfiguration {
  name: string;
  students: Student[];
  type: string;
  gameType: string;
}
