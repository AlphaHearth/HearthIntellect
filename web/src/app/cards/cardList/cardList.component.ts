import {Component, OnInit} from '@angular/core';
import {CardsService} from "../cards.service";

@Component({
  templateUrl: './cardList.component.html',
  styleUrls: ['./cardList.component.css']
})
export class CardListComponent implements OnInit {
  public card = 'test';

  constructor(public service: CardsService) {
  }

  public ngOnInit() {
    // this.service.getCard()
    //   .subscribe(res => this.card = (res[0] as any).name, error => console.log(error));
  }
}
