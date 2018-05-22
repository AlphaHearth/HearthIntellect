import {Component, OnInit} from '@angular/core';
import {CardsService} from "../cards.service";
import {PageEvent} from "@angular/material";

@Component({
  templateUrl: './cardList.component.html',
  styleUrls: ['./cardList.component.css']
})
export class CardListComponent implements OnInit {
  public cards = [];
  public length = 100;
  public pageSizeOptions = [5, 10, 25, 100];

  constructor(public service: CardsService) {
  }

  public ngOnInit() {
    this.service.getCards()
      .subscribe(res => this.cards = (res as any), error => console.log(error));
  }

  public getImgSrc(id: string): string {
    return `https://media.services.zam.com/v1/media/byName/hs/cards/enus/${id}.png`;
  }

  public page(event: PageEvent): void {
    console.log(event.pageIndex);
    console.log(event.pageSize);
    this.service.getCards({page: (event.pageIndex + 1).toString(), pageSize: (event.pageSize).toString()})
      .subscribe(res => this.cards = (res as any), error => console.log(error));
  }
}
