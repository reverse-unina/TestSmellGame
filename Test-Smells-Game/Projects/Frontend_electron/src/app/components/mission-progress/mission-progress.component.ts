import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-mission-progress',
  templateUrl: './mission-progress.component.html',
  styleUrls: ['./mission-progress.component.css']
})
export class MissionProgressComponent {
  @Input() progress: number = 0;
}
