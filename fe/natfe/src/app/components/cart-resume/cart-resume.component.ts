import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/model/user'
import { Cart } from 'src/app/core/model/cart'
import { Item } from 'src/app/core/model/item'
import { Product } from 'src/app/core/model/product'
import { ApiService } from 'src/app/core/api.service'
import { Router } from '@angular/router';
import { MessageService } from 'src/app/core/message.service';

@Component({
  selector: 'app-cart-resume',
  templateUrl: './cart-resume.component.html',
  styleUrls: ['./cart-resume.component.scss']
})
export class CartResumeComponent implements OnInit {

  cart: Cart;
  quantity: string;
  

  constructor(private apiService:ApiService,private router:Router,private messageService:MessageService) { }

  ngOnInit() {
    console.log('ngOnInit resume');
    let user:User = JSON.parse(localStorage.getItem('currentUser'));
    console.log(JSON.stringify(user));
    this.apiService.getUser(user).subscribe(response => {
      console.log(response);
      let responseObj = JSON.parse(JSON.stringify(response));
      console.log(responseObj);
      this.cart = responseObj.cart;
      if (this.cart != undefined && this.cart.items != undefined){
        this.quantity = this.cart.items.length.toString();
      } else {
        this.quantity = '0';
      }
    });
  }

  public goToCheckout(){
    let user = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.closeCheckout(user).subscribe(response => {
      let responseObj = JSON.parse(JSON.stringify(response));
      if (responseObj.status == 'FAIL'){
        console.log(responseObj.errors[0]);
      } else {
        this.messageService.showSuccessMessage('Success','Please go to My purchases to see your status!"');
        this.cart.items = [];
        this.cart.totalValue = '0';
        localStorage.setItem('currentUser', JSON.stringify(responseObj.data));
        this.quantity = '0';
      }
      console.log(response);
    },error => {
      this.messageService.showErrorMessage('Error','Ops! we got an error! try again later!');
    });
    this.router.navigate(['welcome']);
  }

  public deleteCart(product:Product){
    console.log(JSON.stringify(product));
    let user = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.deleteCart(user,product).subscribe(response => {
      let responseObj = JSON.parse(JSON.stringify(response));
      if (responseObj.status == 'FAIL'){
        this.messageService.showErrorMessage('Error',responseObj.errors[0]);
      } else {
        this.messageService.showSuccessMessage('Success','Item has been removed successfully!');
        this.cart = responseObj.data.cart;
        this.quantity = this.cart.items.length.toString();
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
