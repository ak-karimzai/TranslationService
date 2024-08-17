package com.karimzai.tbanktask.Application.Features.Translate.Query.GetTranslationQuery;

import br.com.fluentvalidator.context.ValidationResult;
import com.karimzai.tbanktask.Application.Models.Translate.TranslationInfoDto;
import com.karimzai.tbanktask.Application.Responses.BaseResponse;

public class GetTranslationQueryResponse extends BaseResponse {
    private TranslationInfoDto translation;

    public GetTranslationQueryResponse(ValidationResult validationResult) {
        super(validationResult);
    }

    public void setTranslation(TranslationInfoDto translation) {
        this.translation = translation;
    }

    public TranslationInfoDto getTranslation() {
        return translation;
    }
}
