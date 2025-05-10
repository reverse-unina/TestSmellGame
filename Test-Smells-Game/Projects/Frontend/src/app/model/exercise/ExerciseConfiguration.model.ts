import {Question} from "../question/question.model";

export class RefactoringGameExerciseConfiguration {
  exerciseId!: string;
  className!: string;
  refactoringGameConfiguration!: {
      'dependencies': string,
      'refactoringLimit': number,
      'smellsAllowed': number,
      'level': number
    };
  autoValutative!: boolean;
  availableForGame!: boolean;
}

export class CheckGameExerciseConfiguration {
  exerciseId!: string;
  checkGameConfiguration!: {
    'questions': Question[],
    'level': number
  };
  autoValutative!: boolean;
  availableForGame!: boolean;
}
