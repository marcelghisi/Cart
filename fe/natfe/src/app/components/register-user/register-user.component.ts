import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/model/user';
import { ApiService } from 'src/app/core/api.service'
import { Location } from '@angular/common';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.scss']
})
export class RegisterUserComponent implements OnInit {

  public user = new User();

  constructor(private apiService: ApiService,private location:Location) { }

  ngOnInit() {
  }

  public save(){
    this.apiService.registerUser(this.user).subscribe(response => {
      console.log("Usuario registrado")
    },error => {
      console.log("Erro ao criar usuario",error);
    });
  } 

  goBack(){
    this.location.back();
  }

}
