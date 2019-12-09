import { Injectable } from '@angular/core';
import { Observable } from 'rxjs'
import {HttpParams, HttpClient } from '@angular/common/http'
import * as AppUtils from '../shared/comum/app.utils';
import {UserLogin} from './model/login';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  public baseUrl: string;

  constructor(private httpClient: HttpClient) {
    this.baseUrl = `${AppUtils.BASE_URL}` + 'api/users';
  }

  login(user:UserLogin): Observable <any>{

    const params = new HttpParams()
    .set('username',user.email)
    .set('password',user.password);

    const options = {
      headers: AppUtils.HEADERS_COMMUN,
      params
    }

    return this.httpClient.post(AppUtils.URL_TOKEN,user,options);
  }

  getMainUser(token: any):Observable<any> {
    return this.httpClient.get(`${this.baseUrl}` + '/main',AppUtils.OPTIONS_OBJECTO);
  }

}
