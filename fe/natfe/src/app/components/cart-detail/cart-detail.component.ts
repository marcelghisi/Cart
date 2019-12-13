import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app//core/model/item';
import { Router } from '@angular/router'
import { Cart } from 'src/app/core/model/cart'
import { User } from 'src/app/core/model/user'
import { ApiService } from 'src/app/core/api.service';

@Component({
  selector: 'app-cart-detail',
  templateUrl: './cart-detail.component.html',
  styleUrls: ['./cart-detail.component.scss']
})
export class CartDetailComponent implements OnInit {

  item: Item;
  quantity: string;
  quantityToBuy: string;
  constructor(private apiService:ApiService,private router:Router) { }

  ngOnInit() {
    this.quantityToBuy="1";
    console.log('onInitcartdetail');
    this.item = history.state.data;
    let user:User = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.getUser(user).subscribe(response => {
      console.log(response);
      let responseObj = JSON.parse(JSON.stringify(response));
      let cart = responseObj.cart;
      if (cart != undefined){
        this.quantity = cart.length.toString();
      } else {
        this.quantity = '0';
      }
    });
  }

  public callResume(){
    this.router.navigate(['cart-resume']);
  }

  public addToCart(){
    var cart: Cart = new Cart();
    cart.quantidade = this.quantityToBuy;
    cart.item = this.item;
    let user = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.addCart(user,cart).subscribe(response => {
      let responseObj = JSON.parse(JSON.stringify(response));
      if (responseObj.status == 'FAIL'){
        console.log(responseObj.errors[0]);
      } else {
        console.log("Cart added !")
      }
      console.log(response);
    },error => {
      console.log("Erro adding card",error);
    })
    this.router.navigate(['welcome']);
  }

  public logout(){
    localStorage.setItem('currentUser',null);
    this.router.navigate(['login']);
  }

}
