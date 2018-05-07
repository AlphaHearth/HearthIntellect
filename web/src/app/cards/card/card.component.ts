import {Component, OnInit} from '@angular/core';
import {CardsService} from "../cards.service";

@Component({
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {
  public card = 1;

  constructor(public service: CardsService) {
  }

  public ngOnInit() {
    //   this.service.getCard()
    //     .subscribe(res => this.card = (res[0] as any).name, error => console.log(error));
  }
}
