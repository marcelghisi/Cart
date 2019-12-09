package com.natixis.cart.ws.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuleException.class)
    public ResponseEntity<StandardError> unprocessableEntity(RuleException e, HttpServletRequest request){

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        StandardError err = StandardError.builder().timestamp(System.currentTimeMillis())
                .status(status.value())
                .error("Not found")
                .Message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.unprocessableEntity().body(err);
    }


}
