import {Component, Input, OnDestroy} from '@angular/core';
import {Question} from "../../../model/question/question.model";
import {Answer} from "../../../model/question/answer.model";
import {MatCheckbox} from "@angular/material/checkbox";

@Component({
  selector: 'app-check-smell-answer-list',
  templateUrl: './check-smell-answer-list.component.html',
  styleUrls: ['./check-smell-answer-list.component.css']
})
export class CheckSmellAnswerListComponent implements OnDestroy{
  @Input() questionAnswers!: Answer[];
  @Input() showBackground: boolean = false;
  @Input() disabled: boolean = false;

  selectAnswer(option: Answer) {
    option.isChecked = !option.isChecked;
  }

  changeCheckbox(checkbox: MatCheckbox) {
    checkbox.checked = !checkbox.checked;
  }

  ngOnDestroy(): void {
  }
}
