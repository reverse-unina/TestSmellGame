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

  static fromJson(json: any): RefactoringGameExerciseConfiguration {
    const config = new RefactoringGameExerciseConfiguration();

    config.exerciseId = json.exerciseId;
    config.className = json.class_name;
    config.refactoringGameConfiguration = RefactoringGameConfiguration.fromJson(json.refactoring_game_configuration);
    config.autoValutative = json.auto_valutative;

    return config;
  }
}

class RefactoringGameConfiguration {
  dependencies!: string;
  refactoringLimit!: number;
  smellsAllowed!: number;
  level!: number;
  ignoredSmells!: string[];

  static fromJson(json: any): RefactoringGameConfiguration {
    const config = new RefactoringGameConfiguration();

    config.dependencies = json.dependencies;
    config.refactoringLimit = json.refactoring_limit;
    config.smellsAllowed = json.smells_allowed;
    config.level = json.level;
    config.ignoredSmells = json.ignored_smells;

    return config;
  }
}

export class CheckGameExerciseConfiguration {
  exerciseId!: string;
  checkGameConfiguration!: {
    'questions': Question[],
    'level': number
  };
  autoValutative!: boolean;

  static fromJson(json: any): CheckGameExerciseConfiguration {
    const config = new CheckGameExerciseConfiguration();

    config.exerciseId = json.exerciseId;
    config.checkGameConfiguration = json.check_game_configuration;
    config.autoValutative = json.auto_valutative;

    return config;
  }
}
