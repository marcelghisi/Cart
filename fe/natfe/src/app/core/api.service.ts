import { Injectable } from '@angular/core';
import { Observable } from 'rxjs'
import {HttpParams, HttpClient, HttpHeaders } from '@angular/common/http'
import * as AppUtils from '../shared/comum/app.utils';
import {User} from './model/user';
import {Cart} from './model/cart';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) {
    
  }

  login(user:User): Observable <any>{

    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.post(`${AppUtils.BASE_URL}` + 'api/users/login',user, {
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }


  addCart(user:User,cart:Cart): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.post(`${AppUtils.BASE_URL}` + 'api/users/'+`${user.id}`+'/cart',cart, {
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }

  deleteCart(user:User,cart:Cart): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.delete(`${AppUtils.BASE_URL}` + 'api/users/'+`${user.id}`+'/cart/'+`${cart.item.id}`);
  }

  getUser(user:User): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.get(`${AppUtils.BASE_URL}` + 'api/users/'+`${user.id}`, {
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }

  registerUser(user:User): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.post(`${AppUtils.BASE_URL}` + 'api/users',user, {
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }

  listItems(): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.get(`${AppUtils.BASE_URL}` + 'api/items',{
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }

}
