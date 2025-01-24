export interface TestHistory {
    testId: string;
  userId: number;
  checkScore: number;
  refactoringScore: number;
  correctAnswers: number;
  wrongAnswers: number;
  completionTime: string;
  date: string;
  totalScore: number;
}
