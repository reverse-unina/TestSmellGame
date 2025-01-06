import {HttpClient} from "@angular/common/http";
import {ExerciseService} from "../exercise/exercise.service";
import {LearningContent} from "../../model/learningContent/learning-content";
import {firstValueFrom} from "rxjs";

export class LearningService {
  learning!: LearningContent;

  constructor(
    private exerciseService: ExerciseService,
  ) { }

  async initLearningContent(id: string): Promise<void> {
    this.learning = await firstValueFrom(this.exerciseService.getLeaningContentById(id));
    console.log("content: ", this.learning);
  }


}
