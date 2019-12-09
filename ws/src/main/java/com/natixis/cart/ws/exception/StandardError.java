package com.natixis.cart.ws.exception;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {

    private final static long serialVersionUID = 1L;
    private Long timestamp;
    private Integer status;
    private String error;
    private String Message;
    private String path;
}
