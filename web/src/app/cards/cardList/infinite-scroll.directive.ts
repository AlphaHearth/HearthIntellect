import {AfterViewInit, Directive, ElementRef, Input} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/observable/fromEvent';
import 'rxjs/add/operator/pairwise';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/exhaustMap';
import 'rxjs/add/operator/filter';
import 'rxjs/add/operator/startWith';

interface ScrollPosition {
  scrollHeight: number;
  scrollTop: number;
  clientHeight: number;
}

@Directive({
  selector: '[appInfiniteScroll]'
})
export class InfiniteScrollDirective implements AfterViewInit {
  /** 触发callback函数当前位置离底部距离 */
  @Input() public scrollPosition: number;
  /** callback函数*/
  @Input() public scrollCallback;

  constructor(private elm: ElementRef) {
  }

  public ngAfterViewInit() {
    Observable.fromEvent(this.elm.nativeElement, 'scroll')
      .map((e: any): ScrollPosition => ({
        scrollHeight: e.target.scrollHeight,
        scrollTop: e.target.scrollTop,
        clientHeight: e.target.clientHeight
      }))
      .pairwise()
      .filter(position => this.isScrollDown(position[0], position[1]) && this.isScrollExpectedPosition(position[1]))
      .exhaustMap(() => this.scrollCallback())
      .subscribe();
  }

  /** 判断是否向下滚动 */
  private isScrollDown(prePosition, curPosition) {
    return prePosition.scrollTop < curPosition.scrollTop;
  }

  /** 判断是否已到需要触发callback函数的位置 */
  private isScrollExpectedPosition(curPosition) {
    return (curPosition.scrollHeight - (curPosition.clientHeight + curPosition.scrollTop)) < (this.scrollPosition);
  }
}
