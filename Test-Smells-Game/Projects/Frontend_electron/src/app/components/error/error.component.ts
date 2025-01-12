import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent {
  @Input() title!: string;
  @Input() error!: string;

  refactorErrorMessage() : string[] {
    if (this.error === undefined)
      return [];

    const matchRegexMissingField = this.error.match(/Missing required property "(.*?)" in file (.*)/);
    const matchRegexUnrecognizedField = this.error.match(/Unrecognized field "(.*?)" not marked as ignorable found in file (.*)/);
    const matchRegexReadingFile = this.error.match(/Error reading assignment file (.*)/);

    if (matchRegexMissingField) {
      return [
        matchRegexMissingField[0].split(/ "(.*?)"/)[0],
        `"${matchRegexMissingField[1]}"`,
        "in file",
        matchRegexMissingField[2]
      ];
    } else if (matchRegexUnrecognizedField) {
      return [
        matchRegexUnrecognizedField[0].split(/ "(.*?)"/)[0],
        `"${matchRegexUnrecognizedField[1]}"`,
        "not marked as ignorable found in file",
        matchRegexUnrecognizedField[2]
      ];
    } else if (matchRegexReadingFile) {
      return [
        matchRegexReadingFile[0].split(/ "(.*?)"/)[0],
        matchRegexReadingFile[1]
      ];
    } else {
      return [this.error];
    }
  }
}
