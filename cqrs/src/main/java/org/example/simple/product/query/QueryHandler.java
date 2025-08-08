package org.example.simple.product.query;

public interface QueryHandler<Q extends Query<R>, R> {

    R handleQuery(Q query);

    Class<Q> getQueryClass();
}
