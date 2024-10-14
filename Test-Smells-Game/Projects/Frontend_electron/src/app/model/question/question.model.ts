import {Answer} from "./answer.model";

export class Question{
  questionTitle!: string;
  questionCode!: string;
  answers!: Answer[];

  clearCheckboxes(){
    this.answers.forEach((element)=>{
      element.isChecked=false;
    })

  }
}
