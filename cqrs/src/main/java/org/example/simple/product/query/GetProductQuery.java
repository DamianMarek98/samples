package org.example.simple.product.query;

public record GetProductQuery(Long productId) implements Query<Long> {
}
