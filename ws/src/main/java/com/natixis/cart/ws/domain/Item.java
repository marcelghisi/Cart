package com.natixis.cart.ws.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer quantity;
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return  quantity.equals(item.quantity) &&
                product.equals(item.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, product);
    }
}
