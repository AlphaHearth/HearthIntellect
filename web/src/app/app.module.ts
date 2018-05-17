import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {AppService} from "./app.service";
import {HttpErrorModule} from "./shared/http-error/http-error.module";
import {AppRoutingModule} from "./app-routing.module";
import {MenuModule} from "./menu/menu.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {LayoutModule} from "@angular/cdk/layout";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MenuModule,
    HttpErrorModule,
    LayoutModule,
    AppRoutingModule // 加在所有特性模块（的路由部分）后面
  ],
  providers: [AppService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
