import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {CardsService} from "./cards.service";
import {CardListComponent} from "./cardList/cardList.component";
import {CardComponent} from "./card/card.component";
import {MatCardModule, MatChipsModule} from "@angular/material";


@NgModule({
  declarations: [CardListComponent, CardComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatCardModule,
    MatChipsModule
  ],
  providers: [CardsService]
})
export class CardsModule {
}
