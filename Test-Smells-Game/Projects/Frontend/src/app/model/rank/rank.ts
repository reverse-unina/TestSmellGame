export interface Rank {
  id: number;
  userId: number;
  refactoringScore: number;
  checkSmellScore: number;
  missionsScore: number;
  bestRefactoringScores: { [exerciseId: string]: number };
}

export interface UserRanking {
  gameModeRankings: { [gameMode: string]: number };
  refactoringExerciseRankings: { [exerciseId: string]: number };
}
