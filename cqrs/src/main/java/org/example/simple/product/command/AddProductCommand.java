package org.example.simple.product.command;

public record AddProductCommand(Long productId) implements Command<Long> {
}
