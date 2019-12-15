import { Injectable } from '@angular/core';
import { ToastrService} from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private toastrService:ToastrService) { }

  public showSuccessMessage(titulo:string,message:string){
    this.toastrService.success(titulo,message);
  }

  public showWarningMessage(titulo:string,message:string){
    this.toastrService.warning(titulo,message);
  }

  public showErrorMessage(titulo:string,message:string){
    this.toastrService.error(titulo,message);
  }  
}