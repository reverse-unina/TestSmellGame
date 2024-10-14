export class Exercise {
  exerciseName!: string;
  originalProductionCode!: string;
  originalTestCode!: string;
  refactoredTestCode!: string;


  constructor(exerciseName: string, originalProductionCode: string, originalTestCode: string, refactoredTestCode: string) {
    this.exerciseName = exerciseName;
    this.originalProductionCode = originalProductionCode;
    this.originalTestCode = originalTestCode;
    this.refactoredTestCode = refactoredTestCode;
  }
}
