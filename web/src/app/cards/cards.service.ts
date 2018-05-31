import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

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
  public static host = `http://localhost:4000`;

  constructor(private httpClient: HttpClient) {
  }

  public getCards(searchParam?: { [key: string]: string }) {
    const url = this.getUrl('../cards/', searchParam);
    return this.httpClient.get(url);
  }

  public getUrl(path: string, searchParam: { [key: string]: string | null }): string {
    const url = new window.URL(path, CardsService.host);
    for (const key in searchParam) {
      if (searchParam[key]) {
        url.searchParams.set(key, searchParam[key]);
      }
    }
    return url.toString();
  }
}
