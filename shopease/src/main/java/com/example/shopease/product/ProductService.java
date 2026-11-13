package com.example.shopease.product;


import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
	
	

    private final Map<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Product create(Product p) {
        Long id = idGenerator.getAndIncrement();
        Product saved = new Product(id, p.name(), p.price(), p.stock());
        store.put(id, saved);
        return saved;
    }

    public Optional<Product> update(Long id, Product p) {
        if (!store.containsKey(id)) return Optional.empty();
        Product updated = new Product(id, p.name(), p.price(), p.stock());
        store.put(id, updated);
        return Optional.of(updated);
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}