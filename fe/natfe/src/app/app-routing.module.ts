import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginUserComponent } from './components/login-user/login-user.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';
import { ResendRegistrationTokenComponent } from './components/resend-registration-token/resend-registration-token.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { ListUserComponent } from './components/list-user/list-user.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { CartDetailComponent } from './components/cart-detail/cart-detail.component';
import { CartResumeComponent } from './components/cart-resume/cart-resume.component';

const routes: Routes = [
  { path : '', component : LoginUserComponent},
  { path: 'login', component: LoginUserComponent },
  { path: 'register-user', component: RegisterUserComponent }, 
  { path: 'resend-register-token', component:ResendRegistrationTokenComponent },
  { path: 'welcome', component: WelcomeComponent },
  { path: 'cart-detail', component: CartDetailComponent },
  { path: 'cart-resume', component: CartResumeComponent },
  { path: 'list-user', component: ListUserComponent },
  { path: 'edit-user/:id', component: EditUserComponent }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
