import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/model/user'
import { Cart } from 'src/app/core/model/cart'
import { ApiService } from 'src/app/core/api.service'
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart-resume',
  templateUrl: './cart-resume.component.html',
  styleUrls: ['./cart-resume.component.scss']
})
export class CartResumeComponent implements OnInit {

  carts: Cart[];
  quantity: string;
  

  constructor(private apiService:ApiService,private router:Router) { }

  ngOnInit() {
    console.log('ngOnInit resume');
    let user:User = JSON.parse(localStorage.getItem('currentUser'));
    console.log(JSON.stringify(user));
    this.apiService.getUser(user).subscribe(response => {
      console.log(response);
      let responseObj = JSON.parse(JSON.stringify(response));
      console.log(responseObj);
      this.carts = responseObj.cart;
      if (this.carts != undefined){
        this.quantity = this.carts.length.toString();
      } else {
        this.quantity = '0';
      }
    });
  }

  public deleteCart(cart:Cart){
    let user = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.deleteCart(user,cart).subscribe(response => {
      let responseObj = JSON.parse(JSON.stringify(response));
      if (responseObj.status == 'FAIL'){
        console.log(responseObj.errors[0]);
      } else {
        console.log("Cart deleted !")
        this.carts = responseObj.data.cart;
        this.quantity = this.carts.length.toString();
      }
      console.log(response);
    },error => {
      console.log("Erro deleting card",error);
    })
    this.router.navigate(['cart-resume']);
  }

  public logout(){
    localStorage.setItem('currentUser',null);
    this.router.navigate(['login']);
  }

}
