import {Component, Input, OnInit} from '@angular/core';
import {PodiumRanking} from "../../model/rank/score";

@Component({
  selector: 'app-podium',
  templateUrl: './podium.component.html',
  styleUrls: ['./podium.component.css']
})
export class PodiumComponent implements OnInit{
  @Input() topUsers!: PodiumRanking;

  readonly Object = Object;

  ngOnInit(): void {
    console.log("Podium component");
  }
}
