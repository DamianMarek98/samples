package org.example.simple.product.query;

public class GetProductQueryHandler implements QueryHandler<GetProductQuery, Long> {

    @Override
    public Long handleQuery(GetProductQuery query) {
        return query.productId();
    }

    @Override
    public Class<GetProductQuery> getQueryClass() {
        return GetProductQuery.class;
    }
}
