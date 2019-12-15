import { Component, OnInit } from '@angular/core';
import {ApiService} from '../../core/api.service'
import { User } from 'src/app/core/model/user';
import { Router } from '@angular/router';
import { MessageService } from 'src/app/core/message.service';

@Component({
  selector: 'app-login-user',
  templateUrl: './login-user.component.html',
  styleUrls: ['./login-user.component.scss']
})
export class LoginUserComponent implements OnInit {

  user = new User();

  constructor(private apiService:ApiService,private messageService:MessageService,private router:Router) { }

  ngOnInit() {
  }

  public login(){
    this.apiService.login(this.user).subscribe( data =>{
      console.log(data);
      let responseObj = JSON.parse(JSON.stringify(data))
      if (responseObj.status == 'FAIL'){
        this.messageService.showErrorMessage('Error','User or password invalid');
      } else {
        this.loginSuccess(responseObj.data);
      }
    },error=>{
      this.messageService.showErrorMessage('Error','Error please try again later');
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
    this.messageService.showSuccessMessage('LoggedIn','Welcome ' + user.firstName);
    localStorage.setItem('currentUser', JSON.stringify(user));
    this.router.navigate(['welcome']);
  }

}
