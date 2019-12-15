import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/model/user';
import { ApiService } from 'src/app/core/api.service'
import { Location } from '@angular/common';
import { MessageService } from 'src/app/core/message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.scss']
})
export class RegisterUserComponent implements OnInit {

  public user = new User();

  constructor(private apiService: ApiService,private location:Location,private messageService:MessageService,private router:Router) { }

  ngOnInit() {
  }

  public save(){
    this.apiService.registerUser(this.user).subscribe(response => {
      this.messageService.showSuccessMessage('Success','You have been registered successfully!');
      this.router.navigate(['login']);
    },error => {
      this.messageService.showErrorMessage('Error','Something wrong! please try again later!');
    });
  } 

  goBack(){
    this.location.back();
  }

}
