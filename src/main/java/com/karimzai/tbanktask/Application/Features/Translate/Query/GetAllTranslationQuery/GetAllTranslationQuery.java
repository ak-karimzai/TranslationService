package com.karimzai.tbanktask.Application.Features.Translate.Query.GetAllTranslationQuery;

public class GetAllTranslationQuery {
    private final int pageNumber;
    private final int pageSize;
    private final boolean orderByCreated;

    public GetAllTranslationQuery(int pageNumber, int pageSize, boolean orderByCreated) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderByCreated = orderByCreated;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public boolean isOrderByCreated() {
        return orderByCreated;
    }
}
