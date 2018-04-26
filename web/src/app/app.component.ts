import {Component, OnInit} from '@angular/core';
import {AppService} from "./app.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public card;

  constructor(public service: AppService) {
  }

  public ngOnInit() {
    this.service.getCard()
      .subscribe(res => this.card = (res[0] as any).name, error => console.log(error));
  }
}
