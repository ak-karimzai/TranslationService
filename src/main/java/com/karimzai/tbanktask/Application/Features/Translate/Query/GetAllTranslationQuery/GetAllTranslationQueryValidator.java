package com.karimzai.tbanktask.Application.Features.Translate.Query.GetAllTranslationQuery;

import br.com.fluentvalidator.AbstractValidator;

public class GetAllTranslationQueryValidator extends AbstractValidator<GetAllTranslationQuery> {
    @Override
    public void rules() {
        ruleFor(GetAllTranslationQuery::getPageNumber)
                .must(pageNumber -> pageNumber > 0)
                .withMessage("Номер страницы должен быть больше нуля.");

        ruleFor(GetAllTranslationQuery::getPageSize)
                .must(pageSize -> pageSize > 0 && pageSize <= 20)
                .withMessage("Размер страницы не должен быть меньше или равен нулю и не должен быть больше 20.");
    }
}
