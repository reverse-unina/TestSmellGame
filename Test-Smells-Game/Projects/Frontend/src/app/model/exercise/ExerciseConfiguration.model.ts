import {Question} from "../question/question.model";

export class RefactoringGameExerciseConfiguration {
  exerciseId!: string;
  refactoring_game_configuration!: {
      'dependencies': string,
      'refactoring_limit': number,
      'smells_allowed': number,
      'level': number
    };
    auto_valutative!: boolean;
}

export class CheckGameExerciseConfig {
  exerciseId!: string;
  check_game_configuration!: {
    'questions': Question[],
    'level': number
  };
  auto_valutative!: boolean;
}
