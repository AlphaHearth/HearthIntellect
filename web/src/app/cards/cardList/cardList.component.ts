import {Component, OnInit} from '@angular/core';
import {CardsService} from "../cards.service";

@Component({
  templateUrl: './cardList.component.html',
  styleUrls: ['./cardList.component.css']
})
export class CardListComponent implements OnInit {
  public cards = [];

  constructor(public service: CardsService) {
  }

  public ngOnInit() {
    this.service.getCards()
      .subscribe(res => this.cards = (res as any), error => console.log(error));
  }

  public getImgSrc(id:string):string{
    return `https://media.services.zam.com/v1/media/byName/hs/cards/enus/${id}.png`;
  }
}
