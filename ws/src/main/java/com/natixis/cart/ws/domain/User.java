package com.natixis.cart.ws.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean enabled;
    private Cart cart;
}
