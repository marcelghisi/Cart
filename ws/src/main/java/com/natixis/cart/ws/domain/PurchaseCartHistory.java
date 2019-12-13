package com.natixis.cart.ws.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class PurchaseCartHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String status;
    private Date purchaseDate;
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseCartHistory that = (PurchaseCartHistory) o;
        return purchaseDate.equals(that.purchaseDate) &&
                status.equals(that.status) &&
                user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseDate, status, user);
    }
}
