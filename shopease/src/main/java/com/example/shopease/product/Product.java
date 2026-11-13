package com.example.shopease.product;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record Product(
        Long id,
        @NotBlank(message = "Name is required") String name,
        @NotNull @Positive(message = "Price must be positive") BigDecimal price,
        @PositiveOrZero(message = "Stock cannot be negative") Integer stock
) {}