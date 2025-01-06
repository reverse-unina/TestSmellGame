import {Component, Input, ViewChild} from '@angular/core';
import {ProgressBarMode} from "@angular/material/progress-bar";

@Component({
  selector: 'app-check-smell-question',
  templateUrl: './check-smell-question.component.html',
  styleUrls: ['./check-smell-question.component.css']
})
export class CheckSmellQuestionComponent {
  @Input() editorHeader: string = "title";
  @Input() injectedCode: string = "injectedCode";
  @Input() editable: boolean = true;
  @Input() progressBarMode: ProgressBarMode = "determinate";

  @ViewChild('editorComponent') editorComponent: any;
}
