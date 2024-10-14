import {Question} from "../question/question.model";

export class ExerciseConfiguration {
  exerciseId!: string;
  refactoring_game_configuration!: {
      'dependencies': string,
      'refactoring_limit': number,
      'smells_allowed': number,
      'level': number
    };
    check_game_configuration!: {
      'questions': Question[]
    };
    auto_valutative!: boolean;
}
