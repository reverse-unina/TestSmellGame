import {Component, Input} from '@angular/core';
import {PodiumRanking} from "../../model/rank/score";

@Component({
  selector: 'app-podium',
  templateUrl: './podium.component.html',
  styleUrls: ['./podium.component.css']
})
export class PodiumComponent {
  @Input() topUsers!: PodiumRanking;

  protected readonly Object = Object;
}
