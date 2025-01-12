class RefactoringGameExerciseConfiguration {
  constructor(exerciseId, className, refactoringGameConfigurations, autoValutative) {
    this.exerciseId = exerciseId;
    this.className = className;
    this.refactoringGameConfigurations = refactoringGameConfigurations;
    this.autoValutative = autoValutative;
  }
}



class CheckGameExerciseConfig {
  constructor(exerciseId, checkGameConfiguration, autoValutative) {
    this.exerciseId = exerciseId;
    this.checkGameConfiguration = checkGameConfiguration;
    this.autoValutative = autoValutative;
  }
}

class LearningContent {
  title;
  content;
  externalReference;
}

module.exports = {RefactoringGameExerciseConfiguration, CheckGameExerciseConfig, LearningContent}
