import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';

export interface Card {
  id: string;
  name: string;
  playerClass: string;
  rarity: string;
  set: string;
  text: string;
  type: string;
}

@Injectable()
export class CardsService {

  constructor(private httpClient: HttpClient) {
  }

  public getCards(searchParam?: { [key: string]: string }) {
    const url = this.getUrl('../api/v1/cards/', searchParam);
    return this.httpClient.get(url);
  }

  public getUrl(path: string, searchParam: { [key: string]: string | null }): string {
    const url = new window.URL(path, environment.host);
    for (const key in searchParam) {
      if (searchParam[key]) {
        url.searchParams.set(key, searchParam[key]);
      }
    }
    return url.toString();
  }
}
