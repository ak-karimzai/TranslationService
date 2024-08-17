package com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand;

import br.com.fluentvalidator.AbstractValidator;
import com.karimzai.tbanktask.Application.Contracts.Infrastructure.ITranslationService;
import com.karimzai.tbanktask.Application.Exceptions.ServiceUnavailableException;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TranslateTextCommandValidator extends AbstractValidator<TranslateTextCommand> {
    private final ITranslationService translationService;

    public TranslateTextCommandValidator(ITranslationService translationService) {
        this.translationService = translationService;
    }

    @Override
    public void rules() {
        ruleFor(translateTextCommand -> Objects.equals(translateTextCommand.getSrcLang(), translateTextCommand.getDestLang()))
                .must(equals -> !equals)
                .withMessage("Исходный язык и язык перевода одинаковы.");

        ruleFor(TranslateTextCommand::getText)
                .must(txt -> !txt.isEmpty() && txt.length() <= 256)
                .withMessage("Длина текста должна быть от 1 до 256.");
    }
}
