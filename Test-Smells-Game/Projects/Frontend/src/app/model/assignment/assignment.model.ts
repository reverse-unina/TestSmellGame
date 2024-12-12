export interface Student {
  name: string;
  exercise: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
  submitted: boolean;
}

export interface Assignment {
  name: string;
  students: Student[];
  type: string;
}
