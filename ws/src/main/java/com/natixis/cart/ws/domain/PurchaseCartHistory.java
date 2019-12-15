package com.natixis.cart.ws.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
@EqualsAndHashCode
public class PurchaseCartHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String status;
    private Date purchaseDate;
    private String formattedDate;
    private User user;
}
