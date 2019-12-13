package com.natixis.cart.ws.rules;

import com.natixis.cart.ws.domain.Item;
import com.natixis.cart.ws.exception.RuleException;
import org.springframework.stereotype.Service;

@Service
public class CartRules {

    public void validatePrice(Item item) throws RuleException{
        if (item.getProduct().getPrice() <= 0){
          throw new RuleException("Value needs to be positive");
        }
    }

}
