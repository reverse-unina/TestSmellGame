export interface Student {
  name: string;
  exercise: string;
  start: string;
  end: string;
  submitted: boolean;
}

export interface Assignment {
  name: string;
  date: string;
  students: Student[];
  type: string;
}
