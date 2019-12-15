import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/core/model/product';
import { Router } from '@angular/router'
import { Item } from 'src/app/core/model/item'
import { User } from 'src/app/core/model/user'
import { ApiService } from 'src/app/core/api.service';
import { MessageService } from 'src/app/core/message.service';

@Component({
  selector: 'app-cart-detail',
  templateUrl: './cart-detail.component.html',
  styleUrls: ['./cart-detail.component.scss']
})
export class CartDetailComponent implements OnInit {

  product: Product;
  quantity: string;
  quantityToBuy: string;
  constructor(private apiService:ApiService,private router:Router,private messageService:MessageService ) { }

  ngOnInit() {
    this.quantityToBuy="1";
    this.product = history.state.data;
    let user:User = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.getUser(user).subscribe(response => {
      console.log(response);
      let responseObj = JSON.parse(JSON.stringify(response));
      let cart = responseObj.cart;
      if (cart != undefined){
        this.quantity = cart.items.length.toString();
      } else {
        this.quantity = '0';
      }
    });
  }

  public callResume(){
    this.router.navigate(['cart-resume']);
  }

  public addToCart(){
    var item: Item = new Item();
    item.quantity = this.quantityToBuy;
    item.product = this.product;
    let user = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.addCart(user,item).subscribe(response => {
      let responseObj = JSON.parse(JSON.stringify(response));
      if (responseObj.status == 'FAIL'){
        this.messageService.showErrorMessage('Error',responseObj.errors[0]);
        console.log(responseObj.errors[0]);
      } else {
        this.messageService.showSuccessMessage('Success','Product added to your cart successfully!');
      }
      console.log(response);
    },error => {
      this.messageService.showErrorMessage('Error',error);
    })
    this.router.navigate(['welcome']);
  }

  public logout(){
    localStorage.setItem('currentUser',null);
    this.messageService.showSuccessMessage('Success','Looged out successfully');
    this.router.navigate(['']);
  }

}
