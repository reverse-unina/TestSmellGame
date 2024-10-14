import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';

@Component({
  selector: 'app-logelement',
  templateUrl: './logelement.component.html',
  styleUrls: ['./logelement.component.css']
})
export class LogelementComponent implements AfterViewInit {

  @Input() title!: string;
  @Input() secondarytitle!: string;
  @Input() description!: string;
  @Input() progress = 50;
  @ViewChild('barreference') progbar!: ElementRef;
  constructor() {
  }

  ngAfterViewInit(): void {
    this.progbar.nativeElement.style.width = " " + this.progress.toString() + "%";
  }

}
