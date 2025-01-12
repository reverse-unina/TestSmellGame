import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-achievement-alert',
  templateUrl: './achievement-alert.component.html',
  styleUrls: ['./achievement-alert.component.css']
})
export class AchievementAlertComponent {
  title!: string;
  message!: string;
  showModal: boolean = false;

  show(title: string, message: string): void {
    this.showModal = true;
    this.title = title;
    this.message = message;
  }

  close() {
    this.showModal = false
  }
}
