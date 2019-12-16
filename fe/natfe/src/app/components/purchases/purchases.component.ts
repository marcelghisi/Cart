import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService} from 'src/app/core/api.service';
import { PurchaseCartHistory} from 'src/app/core/model/purchase_cart_history';
import { MessageService } from 'src/app/core/message.service';

@Component({
  selector: 'app-purchases',
  templateUrl: './purchases.component.html',
  styleUrls: ['./purchases.component.scss']
})
export class PurchasesComponent implements OnInit {

  purchases:PurchaseCartHistory[];

  constructor(private router:Router,private apiService:ApiService,private messageService: MessageService) { }

  ngOnInit() {
    let user = JSON.parse(localStorage.getItem('currentUser'));
    this.apiService.listPurchases(user).subscribe( response => {
      console.log('purchases'+response);
      let parsedResponse = JSON.parse(JSON.stringify(response));
      this.purchases = parsedResponse.data;
    })
  }
  public gowelcome(){
    this.router.navigate(['welcome']);
  }

  public logout(){
    localStorage.setItem('currentUser',null);
    this.messageService.showSuccessMessage('Logout','You have logged out successfully!')
    this.router.navigate(['']);
  }
}
