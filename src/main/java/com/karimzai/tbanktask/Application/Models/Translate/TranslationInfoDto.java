package com.karimzai.tbanktask.Application.Models.Translate;

import java.util.UUID;

public class TranslationInfoDto {
    private UUID id;
    private String userIPAddress;
    private String sourceText;
    private String translatedText;

    public TranslationInfoDto() {
    }

    public TranslationInfoDto(UUID id, String userIPAddress, String sourceText, String translatedText) {
        this.id = id;
        this.userIPAddress = userIPAddress;
        this.sourceText = sourceText;
        this.translatedText = translatedText;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserIPAddress() {
        return userIPAddress;
    }

    public void setUserIPAddress(String userIPAddress) {
        this.userIPAddress = userIPAddress;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }
}
