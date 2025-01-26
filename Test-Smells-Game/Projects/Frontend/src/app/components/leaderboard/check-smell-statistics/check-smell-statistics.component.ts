import {Component, Input, OnInit} from '@angular/core';
import {CheckSmellStatistics} from "../../../model/solution/solution";
import {CheckGameExerciseConfiguration} from "../../../model/exercise/ExerciseConfiguration.model";

@Component({
  selector: 'app-check-smell-solution',
  templateUrl: './check-smell-statistics.component.html',
  styleUrls: ['./check-smell-statistics.component.css']
})
export class CheckSmellStatisticsComponent implements OnInit {

  @Input() checkSmellStatistics!: CheckSmellStatistics[];

  ngOnInit(): void {
    this.checkSmellStatistics.sort((a, b) => b.score - a.score);
  }

}

