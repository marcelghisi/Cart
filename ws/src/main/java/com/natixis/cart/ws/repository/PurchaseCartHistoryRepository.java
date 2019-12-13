package com.natixis.cart.ws.repository;

import com.natixis.cart.ws.domain.PurchaseCartHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseCartHistoryRepository extends MongoRepository<PurchaseCartHistory,String> {

    List<PurchaseCartHistory> findAllByUserId(String userId);
}
