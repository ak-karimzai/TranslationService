package com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand;

import br.com.fluentvalidator.context.ValidationResult;
import com.karimzai.tbanktask.Application.Responses.BaseResponse;

public class TranslateTextCommandResponse extends BaseResponse {
    private String translatedText;

    public TranslateTextCommandResponse(ValidationResult validationResult) {
        super(validationResult);
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
