import {SolutionComment} from "../comment/comment";

export class RefactoringSolution {
  solutionId!: number;
  exerciseName!: string;
  playerName!: string;
  refactoredCode!: string;
  commentList!: SolutionComment[];
  creationTimestamp!: Date;
  upVotes!: number;
  downVotes!: number;
  score!: number;
  refactoringResult!: boolean;
  smells!: string;

  constructor(solutionId: number, exerciseName: string, playerName: string, refactoredCode: string, commentList: SolutionComment[], creationTimestamp: Date, upVotes: number, downVotes: number, score: number, refactoringResult: boolean, smells: string) {
    this.solutionId = solutionId;
    this.exerciseName = exerciseName;
    this.playerName = playerName;
    this.refactoredCode = refactoredCode;
    this.commentList = commentList;
    this.creationTimestamp = creationTimestamp;
    this.upVotes = upVotes;
    this.downVotes = downVotes;
    this.score = score;
    this.refactoringResult = refactoringResult;
    this.smells = smells;
  }
}

export class CheckSmellStatistics {
  solutionId: number;
  exerciseName: string;
  playerName: string;
  score: number;
  correctAnswers: number;
  wrongAnswers: number;
  missedAnswers: number;


  constructor(solutionId: number, exerciseName: string, playerName: string, score: number, correctAnswers: number, wrongAnswers: number, missedAnswers: number) {
    this.solutionId = solutionId;
    this.exerciseName = exerciseName;
    this.playerName = playerName;
    this.score = score;
    this.correctAnswers = correctAnswers;
    this.wrongAnswers = wrongAnswers;
    this.missedAnswers = missedAnswers;
  }
}


