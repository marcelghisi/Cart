package com.natixis.cart.ws.exception;

public class UserNotFoundExceptionException extends RuntimeException {
    public UserNotFoundExceptionException(String message){
        super(message);
    }
}
