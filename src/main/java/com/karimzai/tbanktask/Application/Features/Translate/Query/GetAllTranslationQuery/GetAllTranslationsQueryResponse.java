package com.karimzai.tbanktask.Application.Features.Translate.Query.GetAllTranslationQuery;

import br.com.fluentvalidator.context.ValidationResult;
import com.karimzai.tbanktask.Application.Models.Translate.TranslationInfoDto;
import com.karimzai.tbanktask.Application.Responses.BaseResponse;

import java.util.List;

public class GetAllTranslationsQueryResponse extends BaseResponse {
    private List<TranslationInfoDto> translations;

    public GetAllTranslationsQueryResponse(ValidationResult validationResult) {
        super(validationResult);
    }

    public GetAllTranslationsQueryResponse(List<TranslationInfoDto> translations) {
        this.translations = translations;
    }

    public List<TranslationInfoDto> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationInfoDto> translations) {
        this.translations = translations;
    }
}
