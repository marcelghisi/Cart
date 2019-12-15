import { Cart } from 'src/app/core/model/cart';
export class User{
    id?: string;
    email : string;
    firstName : string;
    password: string;
    cart: Cart;
}