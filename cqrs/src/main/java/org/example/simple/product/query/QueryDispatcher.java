package org.example.simple.product.query;

import java.util.HashMap;
import java.util.Map;

/**
 * Considering product package operations are internal this is can be treated as facade
 * for all product read operations
 */
public class QueryDispatcher {

    private final Map<Class<?>, QueryHandler<?, ?>> queryHandlersByType = new HashMap<>();

    public QueryDispatcher() {
        registerQueryHandler(new GetProductQueryHandler());
    }

    @SuppressWarnings("unchecked")
    public <R> R executeQuery(Query<R> query) {
        QueryHandler<Query<R>, R> handler =
            (QueryHandler<Query<R>, R>) queryHandlersByType.get(query.getClass());

        if (handler == null) {
            throw new UnsupportedOperationException("No handler registered for query type: " + query.getClass().getSimpleName());
        }

        return handler.handleQuery(query);
    }

    private <Q extends Query<R>, R> void registerQueryHandler(
            QueryHandler<Q, R> handler) {
        queryHandlersByType.put(handler.getQueryClass(), handler);
    }
}
