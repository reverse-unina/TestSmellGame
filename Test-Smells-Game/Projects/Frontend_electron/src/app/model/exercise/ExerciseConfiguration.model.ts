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
}

export class CheckGameExerciseConfig {
  exerciseId!: string;
  checkGameConfiguration!: {
    'questions': Question[],
    'level': number
  };
  autoValutative!: boolean;

  static fromJson(json: any): CheckGameExerciseConfig {
    const config = new CheckGameExerciseConfig();

    config.exerciseId = json.exerciseId;
    config.checkGameConfiguration = json.check_game_configuration || json.checkGameConfiguration;
    config.autoValutative = json.autoValutative;

    return config;
  }
}
