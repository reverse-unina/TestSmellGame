export class Exercise {
  exerciseName!: string;
  originalProductionCode!: string;
  originalTestCode!: Uint8Array;
  refactoredTestCode!: Uint8Array;


  constructor(exerciseName: string, originalProductionCode: string, originalTestCode: Uint8Array, refactoredTestCode: Uint8Array) {
    this.exerciseName = exerciseName;
    this.originalProductionCode = originalProductionCode;
    this.originalTestCode = originalTestCode;
    this.refactoredTestCode = refactoredTestCode;
  }
}
