package org.example.simple.product.command;

public class AddProductCommandHandler implements CommandHandler<AddProductCommand, Long> {

    @Override
    public Long handleCommand(AddProductCommand command) {
        return command.productId();
    }

    @Override
    public Class<AddProductCommand> getCommandClass() {
        return AddProductCommand.class;
    }
}
