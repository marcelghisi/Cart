package com.natixis.cart.ws.rules;

import com.natixis.cart.ws.exception.RuleException;
import com.natixis.cart.ws.domain.Cart;
import org.springframework.stereotype.Service;

@Service
public class CartRules {

    public void validatePrice(Cart cart) throws RuleException{
        if (cart.getItem().getPrice() <= 0){
          throw new RuleException("Value needs to be positive");
        }
    }

}
