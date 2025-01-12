import {Component, Input} from '@angular/core';
import {ProgressBarMode} from "@angular/material/progress-bar";

@Component({
  selector: 'app-refactoring-compilation-results',
  templateUrl: './refactoring-compilation-results.component.html',
  styleUrls: ['./refactoring-compilation-results.component.css']
})
export class RefactoringCompilationResultsComponent {
  @Input() progressBarMode: ProgressBarMode = "determinate";
  @Input() shellCode: string = "";
  @Input() refactoringWarning: boolean = false;
  @Input() smellNumberWarning: boolean = false;
  @Input() exerciseSuccess: boolean = false;

}
