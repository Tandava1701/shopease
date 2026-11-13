package com.example.shopease.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductService(); // fresh instance for every test
    }

    private Product sample(String name) {
        return new Product(null, name, new BigDecimal("650.00"), 40);
    }

    @Test
    void create_assignsIdAndStoresProduct() {
        Product saved = service.create(sample("Clean Code"));

        assertThat(saved.id()).isEqualTo(1L);
        assertThat(service.findAll()).hasSize(1);
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        assertThat(service.findById(99L)).isEmpty();
    }

    @Test
    void update_changesExistingProduct() {
        Product saved = service.create(sample("Old Name"));

        Optional<Product> updated = service.update(saved.id(), sample("New Name"));

        assertThat(updated).isPresent();
        assertThat(updated.get().name()).isEqualTo("New Name");
    }

    @Test
    void update_returnsEmpty_whenIdDoesNotExist() {
        assertThat(service.update(99L, sample("X"))).isEmpty();
    }

    @Test
    void delete_removesProduct() {
        Product saved = service.create(sample("Clean Code"));

        assertThat(service.delete(saved.id())).isTrue();
        assertThat(service.findAll()).isEmpty();
    }

    @Test
    void delete_returnsFalse_whenIdDoesNotExist() {
        assertThat(service.delete(99L)).isFalse();
    }
}