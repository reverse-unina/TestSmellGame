import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-logtodo',
  templateUrl: './logtodo.component.html',
  styleUrls: ['./logtodo.component.css']
})
export class LogtodoComponent implements OnInit {

  constructor() { }
  todos!: {text: string, checked: boolean}[]


  ngOnInit(): void {
    this.initTodos();
  }

  async initTodos() {
    // @ts-ignore
    await import('./todos.json').then((data) => {
      this.todos = data.todos;
    });
  }


  }
