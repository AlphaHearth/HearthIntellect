import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from '../shared/http-error/not-found.component';
import {MenuComponent} from './menu.component';
import {CardListComponent} from '../cards/cardList/cardList.component';
import {CardComponent} from '../cards/card/card.component';

const routes: Routes = [
  {
    path: 'content',
    component: MenuComponent,
    children: [
      {
        path: '',
        redirectTo: 'cards',
        pathMatch: 'full'
      },
      {
        path: 'cards',
        component: CardListComponent
      },
      {
        path: 'cards/:name',
        component: CardListComponent
      },
      {
        path: 'card/:id',
        component: CardComponent
      },
      // {
      //   path: 'setting',
      //   redirectTo: 'setting', pathMatch: 'full'
      // },
      {
        path: '**',
        component: NotFoundComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MenuRoutingModule {
}
