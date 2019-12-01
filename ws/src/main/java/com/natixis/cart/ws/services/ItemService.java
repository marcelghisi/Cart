package com.natixis.cart.ws.services;

import com.natixis.cart.ws.domain.Item;
import com.natixis.cart.ws.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(String itemId) throws Exception {
        Optional<Item> item = itemRepository.findById(itemId);
        return item.orElseThrow(()-> new Exception("todo"));
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public void delete(String itemId) {
        itemRepository.deleteById(itemId);
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }
}
