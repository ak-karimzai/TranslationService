package com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand;


import com.karimzai.tbanktask.Application.Models.Translate.TranslateTextDto;

public class TranslateTextCommand {
    private final String srcLang;
    private final String destLang;
    private final String text;
    private final String userIP;

    public TranslateTextCommand(TranslateTextDto translateText, String userIP) {
        this.srcLang = translateText.getSrcLang();
        this.destLang = translateText.getDestLang();
        this.text = translateText.getText();
        this.userIP = userIP;
    }

    public TranslateTextCommand(String srcLang, String destLang, String text, String userIP) {
        this.srcLang = srcLang;
        this.destLang = destLang;
        this.text = text;
        this.userIP = userIP;
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

    public String getUserIP() {
        return userIP;
    }
}
