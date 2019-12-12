import { HttpHeaders } from '@angular/common/http'; 

export const BASE_URL = 'http://localhost:8080/';
export const URL_LOGIN = BASE_URL + 'api/users/login';
export const REGISTER_URL = BASE_URL + 'api/users';
export const CONFIRM_REGISTER_URL = BASE_URL + 'api/public/regitrationConfirm/users'; 
export const RESEND_REGISTER_TOKEN_URL = BASE_URL + 'api/public/resendRegistrationToken/users';

const headersToken = new HttpHeaders({
    Authorization: 'Bearer ' + window.localStorage.getItem('accessToken')
});
export const OPTIONS_OBJECTO = { headers: headersToken };
export const HEADERS_LOGIN = new HttpHeaders({
    Authorization: 'Basic ' + btoa('cliente' + ':' + '123')
});
export const HEADERS_REGISTER = new HttpHeaders({
    Authorization: 'Basic ' + btoa('cliente' + ':' + '123')
});
export const HEADERS_COMMUN = new HttpHeaders({
    'Content-Type':  'application/json'
});