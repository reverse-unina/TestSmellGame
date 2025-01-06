import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-learning-page-route',
  templateUrl: './learning-page-route.component.html',
  styleUrls: ['./learning-page-route.component.css']
})
export class LearningPageRouteComponent {
  @Input() title!: string;
  @Input() content!: string;
}
