import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-fail-alert',
  templateUrl: './fail-alert.component.html',
  styleUrls: ['./fail-alert.component.css']
})
export class FailAlertComponent {
  @Input() message: string = 'Unfortunately, you failed the exercise. Don’t give up, try again!';
  showModal: boolean = false;

  show(): void {
    this.showModal = true;
  }

  close() {
    this.showModal = false
  }
}
