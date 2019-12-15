import { Injectable } from '@angular/core';
import { Observable } from 'rxjs'
import {HttpParams, HttpClient, HttpHeaders } from '@angular/common/http'
import * as AppUtils from '../shared/comum/app.utils';
import {User} from './model/user';
import {Cart} from './model/cart';
import { Item } from './model/item';
import { Product } from './model/product';


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


  addCart(user:User,item:Item): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.post(`${AppUtils.BASE_URL}` + 'api/users/'+`${user.id}`+'/cart',item, {
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }

  deleteCart(user:User,product:Product): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );
    return this.httpClient.delete(`${AppUtils.BASE_URL}` + 'api/users/'+`${user.id}`+'/cart/'+`${product.id}`);
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

  closeCheckout(user:User): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );
    console.log('usercheckout'+user);
    return this.httpClient.post(`${AppUtils.BASE_URL}` + 'api/users/'+`${user.id}`+'/cart/checkout', {
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }

  listProducts(): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.get(`${AppUtils.BASE_URL}` + 'api/products',{
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }

  listPurchases(user:User): Observable<any> {
    let headers = new HttpHeaders(
      {'Content-Type':  'application/json',}
      );

    return this.httpClient.get(`${AppUtils.BASE_URL}` + 'api/users/'+`${user.id}`+'/cart/history',{
      headers: new HttpHeaders({
           'Content-Type':  'application/json',
         })
    });
  }

}
