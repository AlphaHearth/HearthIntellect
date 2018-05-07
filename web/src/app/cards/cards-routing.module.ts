import {RouterModule} from "@angular/router";
import {CardListComponent} from "./cardList/cardList.component";
import {CardComponent} from "./card/card.component";
import {NgModule} from "@angular/core";

@NgModule({
  imports: [
    RouterModule.forRoot([
      {
        path: '',
        redirectTo: 'cards',
        pathMatch: 'full'
      },
      {path: 'cards', component: CardListComponent},
      {path: 'card/:id', component: CardComponent}
    ], {useHash: true})
  ],
  exports: [RouterModule]
})
export class CardsRoutingModule {

}
