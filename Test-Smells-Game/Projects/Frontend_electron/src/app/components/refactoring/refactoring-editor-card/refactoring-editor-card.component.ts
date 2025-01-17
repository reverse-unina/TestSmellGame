import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {ProgressBarMode} from "@angular/material/progress-bar";

@Component({
  selector: 'app-refactoring-editor-card',
  templateUrl: './refactoring-editor-card.component.html',
  styleUrls: ['./refactoring-editor-card.component.css']
})
export class RefactoringEditorCardComponent implements OnInit{
  @Input() editorHeader: string = "title";
  @Input() injectedCode: string = "injectedCode";
  @Input() editable: boolean = true;
  @Input() progressBarMode: ProgressBarMode = "determinate";

  @ViewChild('editorComponent') editorComponent: any;

  ngOnInit(): void {
    console.log("refactoring-editor-card");
  }
}
