import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/core/api.service'
import { Router } from '@angular/router';
import { Item } from 'src/app/core/model/item'
import { Cart } from 'src/app/core/model/cart'
import { User } from 'src/app/core/model/user'
import { parse } from 'querystring';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent implements OnInit {

  items: Item[];
  cart: Cart[];
  quantity:string;

  constructor(private apiService: ApiService,private router:Router) {

   }


  ngOnInit() {
    
    let user:User = JSON.parse(localStorage.getItem('currentUser'));
    console.log(JSON.stringify(user));
    this.apiService.getUser(user).subscribe(response => {
      console.log(response);
      let responseObj = JSON.parse(JSON.stringify(response));
      console.log(responseObj);
      console.log('aqui');
      this.cart = responseObj.cart;
      if (this.cart != undefined){
        this.quantity = this.cart.length.toString();
      } else {
        this.quantity = '0';
      }
    });

    console.log(this.quantity);
    
    this.apiService.listItems().subscribe( response => {
      this.items = JSON.parse(JSON.stringify(response));
      console.log(this.items);
    })
  }

  public callResume(){
    this.router.navigate(['cart-resume']);
  }
  
  public callProductDetail(item: Item){
    console.log(item);
    this.router.navigate(['cart-detail'],{state: {data: item}});
  }

}
