package com.karimzai.tbanktask.Application.Features.Translate.Query.GetTranslationQuery;

import br.com.fluentvalidator.AbstractValidator;

import java.util.UUID;

public class GetTranslationQueryValidator extends AbstractValidator<GetTranslationQuery> {
    @Override
    public void rules() {
        ruleFor(GetTranslationQuery::getId)
                .must(this::validateUUID)
                .withMessage("неверный идентификатор перевода.");
    }

    private boolean validateUUID(String id) {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
