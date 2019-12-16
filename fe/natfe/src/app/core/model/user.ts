import { Cart } from 'src/app/core/model/cart';
export class User{
    id?: string;
    email : string;
    firstName : string;
    lastName : string;
    password: string;
    cart: Cart;
}