import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-success-alert',
  templateUrl: './success-alert.component.html',
  styleUrls: ['./success-alert.component.css']
})
export class SuccessAlertComponent {
  message: string = "You have completed successfully the exercise.";
  showModal: boolean = false;

  show(message?: string): void {
    if (message) {
      this.message = message;
    }
    this.showModal = true;
  }

  close() {
    this.showModal = false
  }
}
