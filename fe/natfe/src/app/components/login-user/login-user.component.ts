import { Component, OnInit } from '@angular/core';
import {ApiService} from '../../core/api.service'
import { User } from 'src/app/core/model/user';
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
      console.log(data);
      let responseObj = JSON.parse(JSON.stringify(data))
      if (responseObj.status == 'FAIL'){
        console.log('User or password invalid')
      } else {
        this.loginSuccess(responseObj.data);
      }
    },error=>{
      console.log('Error ao fazer login');
    });
  }
  
  public loginSuccess(data: any) {
    localStorage.clear();
    let user: User = data;
    this.redirectPage(user);

  }

  /**
   * name
   */
  public redirectPage(user: User) {
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.router.navigate(['welcome']);
  }

}
