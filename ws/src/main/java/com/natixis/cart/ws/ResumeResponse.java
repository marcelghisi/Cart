package com.natixis.cart.ws;

import com.natixis.cart.ws.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResumeResponse {
    String status;
    User data;
    List<String> errors;
}
