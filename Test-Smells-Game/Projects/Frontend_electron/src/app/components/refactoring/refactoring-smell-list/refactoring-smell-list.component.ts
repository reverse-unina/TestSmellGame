import {Component, Input} from '@angular/core';
import {SmellDescription} from "../../../model/SmellDescription/SmellDescription.model";

@Component({
  selector: 'app-refactoring-smell-list',
  templateUrl: './refactoring-smell-list.component.html',
  styleUrls: ['./refactoring-smell-list.component.css']
})
export class RefactoringSmellListComponent {
  @Input() smellList: string[] = [];
  @Input() smellDescriptions: SmellDescription[] = [];
  @Input() methodList: string[] = [];

  getSmellNumber(smell: string) {
    switch (smell){
      case 'Assertion Roulette':
        return 0;
      case 'Conditional Test Logic':
        return 1;
      case 'Constructor Initialization':
        return 2;
      case 'Default Test':
        return 3;
      case 'Duplicate Assert':
        return 4;
      case 'Eager Test':
        return 5;
      case 'Empty Test':
        return 6;
      case 'Exception Handling':
        return 7;
      case 'General Fixture':
        return 8;
      case 'Ignored Test':
        return 9;
      case 'Lazy Test':
        return 10;
      case 'Magic Number Test':
        return 11;
      case 'Mystery Guest':
        return 12;
      case 'Print Statement':
        return 13;
      case 'Redundant Assertion':
        return 14;
      case 'Resource Optimism':
        return 15;
      case 'Sensitive Equality':
        return 16;
      case 'Sleepy Test':
        return 17;
      case 'Unknown Test':
        return 18;
      default:
        return 19;
    }
  }


}
