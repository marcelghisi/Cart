package com.natixis.cart.ws.services;

import com.natixis.cart.ws.domain.Product;
import com.natixis.cart.ws.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Product create(final Product product) {
        return itemRepository.save(product);
    }

    public Product findById(String itemId) throws Exception {
        final Optional<Product> item = itemRepository.findById(itemId);
        return item.orElseThrow(()-> new Exception("todo"));
    }

    public List<Product> findAll() {
        return itemRepository.findAll();
    }

    public void delete(final String itemId) {
        itemRepository.deleteById(itemId);
    }

    public Product update(final Product product) {
        return itemRepository.save(product);
    }
}
