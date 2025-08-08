package org.example.simple.product.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Considering product package operations are internal this is can be treated as facade
 * for all product operations with side effects
 */
public class CommandDispatcher {

    private final Map<Class<?>, CommandHandler<?, ?>> commandHandlersByType = new HashMap<>();

    public CommandDispatcher() {
        registerCommandHandler(new AddProductCommandHandler());
    }

    @SuppressWarnings("unchecked")
    public <R> R executeCommand(Command<R> command) {
        CommandHandler<Command<R>, R> handler =
            (CommandHandler<Command<R>, R>) commandHandlersByType.get(command.getClass());

        if (handler == null) {
            throw new UnsupportedOperationException("No handler registered for command type: " + command.getClass().getSimpleName());
        }

        return handler.handleCommand(command);
    }

    private <C extends Command<T>, T> void registerCommandHandler(
            CommandHandler<C, T> handler) {
        commandHandlersByType.put(handler.getCommandClass(), handler);
    }
}
