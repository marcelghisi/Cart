package com.natixis.cart.ws;

import com.natixis.cart.ws.domain.PurchaseCartHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class HistoryResponse {
    String status;
    List<PurchaseCartHistory> data;
    List<String> errors;
}
