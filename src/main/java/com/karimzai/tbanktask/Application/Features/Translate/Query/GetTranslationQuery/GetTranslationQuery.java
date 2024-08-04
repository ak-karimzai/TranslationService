package com.karimzai.tbanktask.Application.Features.Translate.Query.GetTranslationQuery;

import java.util.UUID;

public class GetTranslationQuery {
    private final String id;
    public GetTranslationQuery(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
