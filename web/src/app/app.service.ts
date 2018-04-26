import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable()
export class AppService {

  constructor(private httpClient: HttpClient) {
  }

  public getCard(){
    return this.httpClient.get("http://localhost:4000/cards");
  }
}
