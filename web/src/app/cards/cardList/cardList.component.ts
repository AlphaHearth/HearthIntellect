import {Component, OnInit} from '@angular/core';
import {CardsService} from '../cards.service';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';

import {ActivatedRoute} from '@angular/router';
import {MatSnackBar} from '@angular/material';


@Component({
  templateUrl: './cardList.component.html',
  styleUrls: ['./cardList.component.css']
})
export class CardListComponent implements OnInit {
  public cards = [];
  private pageIndex;
  private searchVale = null;
  public isError: boolean;

  public scrollCallBack;

  constructor(private route: ActivatedRoute, private service: CardsService, private snackBar: MatSnackBar) {
    this.pageIndex = 1;
    this.isError = false;
    this.scrollCallBack = this.scrollGetCard.bind(this);
  }

  public ngOnInit() {
    this.route.params.subscribe(() => {
      this.searchVale = this.route.snapshot.paramMap.get('name') === 'null' ? null : this.route.snapshot.paramMap.get('name');
      this.service.getCards({search: this.searchVale})
        .subscribe(res => {
            this.cards = (res as any);
            this.isError = false;
          }, error => {
            this.openSnackBar(JSON.stringify(error));
            this.isError = true;
          }
        );
    });
  }

  public getImgSrc(id: string): string {
    return `https://media.services.zam.com/v1/media/byName/hs/cards/enus/${id}.png`;
  }

  public scrollGetCard() {
    return this.service.getCards({page: (++this.pageIndex).toString(), search: this.searchVale})
      .do((val) => {
        this.cards = this.cards.concat(val);
        this.isError = false;
      });
  }

  private openSnackBar(message: string) {
    this.snackBar.open(message, '', {duration: 3e3});
  }
}
