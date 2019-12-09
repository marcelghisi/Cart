import { Component, OnInit } from '@angular/core';
import {ApiService} from '../../core/api.service'
import { User } from 'src/app/core/model/login';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-user',
  templateUrl: './login-user.component.html',
  styleUrls: ['./login-user.component.scss']
})
export class LoginUserComponent implements OnInit {

  user = new User();

  constructor(private apiService:ApiService,private router:Router) { }

  ngOnInit() {
  }

  public login(){
    this.apiService.login(this.user).subscribe( data =>{
      this.loginSuccess(data);
    },error=>{
      console.log('Error ao fazer login');
    });
  }
  
  public loginSuccess(data: any) {
    localStorage.clear();
    this.apiService.login(data).subscribe(data => {
      this.redirectPage(data);
    }, error => {
      console.log('Error getting user');
    });
  }

  /**
   * name
   */
  public redirectPage(user: any) {
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.router.navigate(['welcome']);
  }

}
