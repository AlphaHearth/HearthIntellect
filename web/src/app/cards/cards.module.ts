import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {CardsService} from './cards.service';
import {CardListComponent} from './cardList/cardList.component';
import {CardComponent} from './card/card.component';
import {MatCardModule, MatChipsModule, MatSnackBarModule} from '@angular/material';
import {InfiniteScrollDirective} from './cardList/infinite-scroll.directive';


@NgModule({
  declarations: [CardListComponent, CardComponent, InfiniteScrollDirective],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatCardModule,
    MatChipsModule,
    MatSnackBarModule
  ],
  providers: [CardsService]
})
export class CardsModule {
}
