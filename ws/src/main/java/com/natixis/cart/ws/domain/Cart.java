package com.natixis.cart.ws.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer quantidade;
    private Item item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return  quantidade.equals(cart.quantidade) &&
                item.equals(cart.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantidade,item);
    }
}
