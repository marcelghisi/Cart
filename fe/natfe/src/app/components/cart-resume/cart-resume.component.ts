import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/model/user'
import { Cart } from 'src/app/core/model/cart'
import { ApiService } from 'src/app/core/api.service'

@Component({
  selector: 'app-cart-resume',
  templateUrl: './cart-resume.component.html',
  styleUrls: ['./cart-resume.component.scss']
})
export class CartResumeComponent implements OnInit {

  carts: Cart[];

  constructor(private apiService:ApiService) { }

  ngOnInit() {
    let user:User = JSON.parse(localStorage.getItem('currentUser'));
    console.log(JSON.stringify(user));
    this.apiService.getUser(user).subscribe(response => {
      console.log(response);
      let responseObj = JSON.parse(JSON.stringify(response));
      console.log(responseObj);
      this.carts = responseObj.cart;
    });
  }

}
