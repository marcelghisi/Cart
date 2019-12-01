package com.natixis.cart.ws.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String firstName;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                firstName.equals(user.firstName) &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, email);
    }
}
