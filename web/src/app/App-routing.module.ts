import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from './shared/http-error/not-found.component';

const routes: Routes = [
  {path: '', redirectTo: 'cards', pathMatch: 'full'},
 // {path: 'setting', redirectTo: 'setting', pathMatch: 'full'}, // 登录成功后导航至 index 页面 此时默认打开 Handicap 页面
  {path: '**', component: NotFoundComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
