package com.natixis.cart.ws.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private Double totalValue;
    private List<Item> items;
}
