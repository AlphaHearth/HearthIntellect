import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {NotFoundComponent} from './not-found.component';



@NgModule({
  declarations: [
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ]
})
export class HttpErrorModule { }
