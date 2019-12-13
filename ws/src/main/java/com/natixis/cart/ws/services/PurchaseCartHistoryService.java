package com.natixis.cart.ws.services;

import com.natixis.cart.ws.domain.PurchaseCartHistory;
import com.natixis.cart.ws.repository.PurchaseCartHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseCartHistoryService {

    @Autowired
    PurchaseCartHistoryRepository purchaseCartHistoryRepository;

    public List<PurchaseCartHistory> findAll(){
       return purchaseCartHistoryRepository.findAll();
    }

    public PurchaseCartHistory create(PurchaseCartHistory purchaseCartHistory){
        return purchaseCartHistoryRepository.save(purchaseCartHistory);
    }

    public List<PurchaseCartHistory> findByUserId(String userId) {
        return purchaseCartHistoryRepository.findAllByUserId(userId);
    }
}
