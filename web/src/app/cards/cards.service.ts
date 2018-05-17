import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable()
export class CardsService {

    constructor(private httpClient: HttpClient) {
    }

    public getCards(){
        return this.httpClient.get("http://localhost:4000/cards");
    }
}
