import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";
import {MenuComponent} from "./menu.component";
import {MatCardModule} from "@angular/material";

@NgModule({
  declarations: [MenuComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatCardModule
  ],
  exports: [MenuComponent]
})
export class MenuModule {

}
