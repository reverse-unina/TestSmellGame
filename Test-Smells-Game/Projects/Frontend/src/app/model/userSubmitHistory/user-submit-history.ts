export class UserSubmitHistory {
  userId: number;
  exerciseType: string;
  exerciseScore: number;
  exerciseName: string;
  dateTime: number[];

  constructor(userId: number, exerciseType: string, exerciseScore: number, exerciseName: string, dateTime: number[]) {
    this.userId = userId;
    this.exerciseType = exerciseType;
    this.exerciseScore = exerciseScore;
    this.exerciseName = exerciseName;
    this.dateTime = dateTime;
  }
}
