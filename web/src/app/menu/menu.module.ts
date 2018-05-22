import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";
import {MenuComponent} from "./menu.component";
import {
  MatButtonModule, MatFormFieldModule, MatIconModule, MatInputModule, MatListModule, MatSidenavModule,
  MatToolbarModule
} from "@angular/material";
import {CardsModule} from "../cards/cards.module";
import {MenuRoutingModule} from "./menu-routing.module";

@NgModule({
  declarations: [MenuComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatToolbarModule,
    MatIconModule,
    MatListModule,
    MatButtonModule,
    MatSidenavModule,
    CardsModule,
    MenuRoutingModule,
    MatFormFieldModule,
    MatInputModule
  ],
  exports: [MenuComponent]
})
export class MenuModule {
}
