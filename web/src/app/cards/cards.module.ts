import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {CardsService} from "./cards.service";
import {CardListComponent} from "./cardList/cardList.component";
import {CardComponent} from "./card/card.component";
import {CardsRoutingModule} from "./cards-routing.module";


@NgModule({
  declarations: [CardListComponent, CardComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    CardsRoutingModule
  ],
  providers: [CardsService]
})
export class CardsModule {
}
