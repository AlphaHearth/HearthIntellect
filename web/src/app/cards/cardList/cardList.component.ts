import {Component, OnInit} from '@angular/core';
import {CardsService} from '../cards.service';
import 'rxjs/add/operator/do';
import {ActivatedRoute} from '@angular/router';

@Component({
  templateUrl: './cardList.component.html',
  styleUrls: ['./cardList.component.css']
})
export class CardListComponent implements OnInit {
  public cards = [];
  private pageIndex;
  private searchVale = null;

  public scrollCallBack;

  constructor(private route: ActivatedRoute, private service: CardsService) {
    this.pageIndex = 1;
    this.scrollCallBack = this.scrollGetCard.bind(this);
  }

  public ngOnInit() {
    this.route.params.subscribe(() => {
      this.searchVale = this.route.snapshot.paramMap.get('name');
      console.log('search', this.searchVale);
      this.service.getCards({search: this.searchVale})
        .subscribe(res => this.cards = (res as any), error => console.log(error));
    });
  }

  public getImgSrc(id: string): string {
    return `https://media.services.zam.com/v1/media/byName/hs/cards/enus/${id}.png`;
  }

  public scrollGetCard() {
    return this.service.getCards({page: (++this.pageIndex).toString(), search: this.searchVale})
      .do((val) => {
        this.cards = this.cards.concat(val);
      });
  }
}
