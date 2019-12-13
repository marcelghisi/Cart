package com.natixis.cart.ws.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    private Double totalValue;
    private List<Item> items;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return  totalValue.equals(cart.totalValue) &&
                items.equals(cart.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalValue, items);
    }
}
