import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/core/api.service'
import { Router, RouterLink } from '@angular/router';
import { Product } from 'src/app/core/model/product'
import { Cart } from 'src/app/core/model/cart'
import { User } from 'src/app/core/model/user'
import { Item } from 'src/app/core/model/item'
import { MessageService } from 'src/app/core/message.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {

  products: Product[];
  cart: Cart;
  quantity:string;
  user:User;

  constructor(private apiService: ApiService,private router:Router,private messageService:MessageService) {

   }


  ngOnInit() {
    console.log('ngOnInit welcome');
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    console.log(this.user);
    if (this.user != null) {
      console.log(JSON.stringify(this.user));
      this.apiService.getUser(this.user).subscribe(response => {
        console.log(response);
        let responseObj = JSON.parse(JSON.stringify(response));
        console.log(responseObj);
        this.cart = responseObj.cart;
        if (this.cart != undefined){
          console.log('cart'+this.cart)
          this.quantity = this.cart.items.length.toString();
        } else {
          this.quantity = '0';
        }
      });
      console.log(this.quantity);
      this.apiService.listProducts().subscribe( response => {
        console.log('sdasd'+response);
        this.products = JSON.parse(JSON.stringify(response));
      })
    } else {
      this.router.navigate(['login']);
    }
  }

  public callResume(){
    this.router.navigate(['cart-resume']);
  }

  public callProductDetail(product: Product){
    this.router.navigate(['cart-detail'],{state: {data: product}});
  }

  public logout(){
    localStorage.setItem('currentUser',null);
    this.messageService.showSuccessMessage('Logout','You have logged out successfully!')
    this.router.navigate(['']);
  }

  public purchases(){
    this.router.navigate(['purchases']);
  }
}
