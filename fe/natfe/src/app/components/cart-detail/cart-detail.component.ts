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
  constructor(private apiService:ApiService,private router:Router) { }

  ngOnInit() {
    this.item = history.state.data;
    let user:User = JSON.parse(localStorage.getItem('currentUser'));

    this.apiService.getUser(user).subscribe(response => {
      let cart = JSON.parse(response).data.cart;
      if (cart != undefined){
        this.quantity = cart.length.toString();
      } else {
        this.quantity = '0';
      }
    });
  }

  public addToCart(){
    var cart: Cart = new Cart();
    cart.quantidade = this.quantity;
    cart.item = this.item;
    let user = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.addCart(user,cart).subscribe(response => {
      console.log("Cart added !")
    },error => {
      console.log("Erro adding card",error);
    })
    this.router.navigate(['welcome']);
  }

}
