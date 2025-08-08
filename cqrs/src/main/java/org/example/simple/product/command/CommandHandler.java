package org.example.simple.product.command;

public interface CommandHandler<C extends Command<R>, R> {

    R handleCommand(C command);

    Class<C> getCommandClass();
}
