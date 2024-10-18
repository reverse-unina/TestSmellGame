import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { marked } from 'marked';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-home-route',
  templateUrl: './home-route.component.html',
  styleUrls: ['./home-route.component.css']
})
export class HomeRouteComponent implements OnInit {

  readmeContent: string = "";

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadReadmeContent();
  }

  async loadReadmeContent(): Promise<void> {
    try {
      const data = await this.http.get(environment.exerciseServiceUrl + '/files/readme', { responseType: 'text' }).toPromise();
      if (typeof data === 'string') {
        this.readmeContent = await marked(data);
      } else {
        console.error('Received data is not a string:', data);
      }
    } catch (error) {
      console.error('Error loading README content:', error);
    }
  }
}
