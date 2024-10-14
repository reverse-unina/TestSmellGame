import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-logviewer',
  templateUrl: './logviewer.component.html',
  styleUrls: ['./logviewer.component.css']
})
export class LogviewerComponent implements OnInit {

  logs!: {title:string, secondarytitle: string, description: string, progress:number}[]
  title!: []


  constructor() {
  }

  ngOnInit(): void {
    this.initLogs()
  }


  async initLogs() {
    // @ts-ignore
    await import('./logs.json').then((data) => {
      this.logs = data.logs;
      this.title = data.title;
    });
  }

}
