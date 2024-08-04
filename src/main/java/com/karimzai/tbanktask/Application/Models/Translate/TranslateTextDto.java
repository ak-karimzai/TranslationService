package com.karimzai.tbanktask.Application.Models.Translate;

public class TranslateTextDto {
    private final String srcLang;
    private final String destLang;
    private final String text;

    public TranslateTextDto(String srcLang, String destLang, String text) {
        this.srcLang = srcLang;
        this.destLang = destLang;
        this.text = text;
    }

    public String getSrcLang() {
        return srcLang;
    }

    public String getDestLang() {
        return destLang;
    }

    public String getText() {
        return text;
    }
}
