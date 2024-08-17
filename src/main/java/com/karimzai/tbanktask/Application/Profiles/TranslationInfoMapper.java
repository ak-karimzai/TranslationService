package com.karimzai.tbanktask.Application.Profiles;

import com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand.TranslateTextCommand;
import com.karimzai.tbanktask.Application.Models.Translate.TranslationInfoDto;
import com.karimzai.tbanktask.Domain.Entities.TranslationInfo;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TranslationInfoMapper {
    public TranslationInfo mapTranslateTextCommand(TranslateTextCommand translateTextCommand, String translatedText) {
        TranslationInfo translationInfo = new TranslationInfo();
        translationInfo.setSourceText(translateTextCommand.getText());
        translationInfo.setTranslatedText(translatedText);
        translationInfo.setUserIPAddress(translateTextCommand.getUserIP());
        return translationInfo;
    }

    public TranslationInfoDto mapToDto(Optional<TranslationInfo> translationInfo) {
        if (translationInfo.isEmpty()) {
            return null;
        }
        TranslationInfo unboxed = translationInfo.get();
        TranslationInfoDto translationInfoDto = new TranslationInfoDto();
        translationInfoDto.setId(unboxed.getId());
        translationInfoDto.setSourceText(unboxed.getSourceText());
        translationInfoDto.setTranslatedText(unboxed.getTranslatedText());
        translationInfoDto.setUserIPAddress(unboxed.getUserIPAddress());
        return translationInfoDto;
    }

    public TranslationInfoDto mapToDto(TranslationInfo translationInfo) {
        TranslationInfoDto translationInfoDto = new TranslationInfoDto();
        translationInfoDto.setId(translationInfo.getId());
        translationInfoDto.setSourceText(translationInfo.getSourceText());
        translationInfoDto.setTranslatedText(translationInfo.getTranslatedText());
        translationInfoDto.setUserIPAddress(translationInfo.getUserIPAddress());
        return translationInfoDto;
    }
}
