import {Component, Input, OnDestroy} from '@angular/core';

@Component({
  selector: 'app-fail-alert',
  templateUrl: './fail-alert.component.html',
  styleUrls: ['./fail-alert.component.css']
})
export class FailAlertComponent implements OnDestroy {
  message: string = 'Unfortunately, you failed the exercise. Don’t give up, try again!';
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

  ngOnDestroy(): void {
  }
}
