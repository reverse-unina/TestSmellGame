export interface Score {
  id: number;
  userName: string;
  refactoringScore: number;
  checkSmellScore: number;
  missionsScore: number;
  bestRefactoringScores: { [exerciseId: string]: number };
}

export interface UserRanking {
  gameModeRankings: { [gameMode: string]: number };
  refactoringExerciseRankings: { [exerciseId: string]: number };
}

export interface PodiumRanking {
  [key: string]: [
    {
      userName: string[];
      score: number;
    }
  ];
}
