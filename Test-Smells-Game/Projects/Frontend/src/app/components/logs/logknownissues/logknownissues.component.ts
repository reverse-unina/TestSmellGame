import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-logknownissues',
  templateUrl: './logknownissues.component.html',
  styleUrls: ['./logknownissues.component.css']
})
export class LogknownissuesComponent implements OnInit {

  constructor() { }

  issues !: {text: string, checked: boolean}[]
  ngOnInit(): void {
    this.initIssues()
  }

  async initIssues() {
    // @ts-ignore
    await import('./issues.json').then((data) => {
      this.issues = data.issues;
    });
  }

}
