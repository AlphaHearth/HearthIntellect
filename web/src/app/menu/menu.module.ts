import {NgModule} from "@angular/core";
import {HttpClientModule} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";
import {MenuComponent} from "./menu.component";

@NgModule({
  declarations: [MenuComponent],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  exports: [MenuComponent]
})
export class MenuModule {

}
