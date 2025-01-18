import {Component, EventEmitter, Input, OnDestroy, Output} from '@angular/core';

@Component({
  selector: 'app-success-alert',
  templateUrl: './success-alert.component.html',
  styleUrls: ['./success-alert.component.css']
})
export class SuccessAlertComponent implements OnDestroy {
  @Input() message: string = "You have completed successfully the exercise.";
  showModal: boolean = false;

  show(): void {
    this.showModal = true;
  }

  close() {
    this.showModal = false
  }

  ngOnDestroy(): void {
  }
}
