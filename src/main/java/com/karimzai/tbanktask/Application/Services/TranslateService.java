package com.karimzai.tbanktask.Application.Services;

import br.com.fluentvalidator.context.ValidationResult;
import com.karimzai.tbanktask.Application.Contracts.Infrastructure.ITranslationService;
import com.karimzai.tbanktask.Application.Contracts.Presistence.ITranslationInfoRepository;
import com.karimzai.tbanktask.Application.Exceptions.ServiceUnavailableException;
import com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand.TranslateTextCommand;
import com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand.TranslateTextCommandResponse;
import com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand.TranslateTextCommandValidator;
import com.karimzai.tbanktask.Application.Features.Translate.Query.GetAllTranslationQuery.GetAllTranslationQuery;
import com.karimzai.tbanktask.Application.Features.Translate.Query.GetAllTranslationQuery.GetAllTranslationsQueryResponse;
import com.karimzai.tbanktask.Application.Features.Translate.Query.GetAllTranslationQuery.GetAllTranslationQueryValidator;
import com.karimzai.tbanktask.Application.Features.Translate.Query.GetTranslationQuery.GetTranslationQuery;
import com.karimzai.tbanktask.Application.Features.Translate.Query.GetTranslationQuery.GetTranslationQueryResponse;
import com.karimzai.tbanktask.Application.Features.Translate.Query.GetTranslationQuery.GetTranslationQueryValidator;
import com.karimzai.tbanktask.Application.Models.Translate.TranslationInfoDto;
import com.karimzai.tbanktask.Application.Profiles.TranslationInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TranslateService {
    private final ITranslationInfoRepository translationInfoRepository;
    private final ITranslationService translationService;
    private final TranslationInfoMapper translationInfoMapper;

    public TranslateService(ITranslationInfoRepository translationInfoRepository, ITranslationService translationService, TranslationInfoMapper translationInfoMapper) {
        this.translationInfoRepository = translationInfoRepository;
        this.translationService = translationService;
        this.translationInfoMapper = translationInfoMapper;
    }

    public TranslateTextCommandResponse translate(TranslateTextCommand translateTextCommand) throws ServiceUnavailableException {
        TranslateTextCommandValidator validator = new TranslateTextCommandValidator(translationService);
        ValidationResult validationResult = validator.validate(translateTextCommand);
        TranslateTextCommandResponse translateTextCommandResponse = new TranslateTextCommandResponse(validationResult);
        var isSupportSourceLang = translationService.existLanguage(translateTextCommand.getSrcLang());
        var isSupportTargetLang = translationService.existLanguage(translateTextCommand.getDestLang());
        if (!isSupportSourceLang) {
            translateTextCommandResponse.addError("Исходный язык не поддерживается.");
        }
        if (!isSupportTargetLang) {
            translateTextCommandResponse.addError("Язык перевода не поддерживаются.");
        }

        if (translateTextCommandResponse.isSuccess()) {
            var translatedText = translationService.translate(translateTextCommand.getText(),
                    translateTextCommand.getSrcLang(), translateTextCommand.getDestLang());
            var translationInfo = translationInfoMapper.mapTranslateTextCommand(translateTextCommand, translatedText);
            translationInfoRepository.saveTranslationInfo(translationInfo);
            translateTextCommandResponse.setTranslatedText(translatedText);
        }

        return translateTextCommandResponse;
    }

    public GetTranslationQueryResponse getTranslationInfo(GetTranslationQuery getTranslationQuery) {
        var validator = new GetTranslationQueryValidator();
        var validationResult = validator.validate(getTranslationQuery);
        GetTranslationQueryResponse getTranslationQueryResponse = new GetTranslationQueryResponse(validationResult);

        if (getTranslationQueryResponse.isSuccess()) {
            var translationInfo = translationInfoRepository
                    .getTranslationInfo(UUID.fromString(getTranslationQuery.getId()));
            var translationViewModel = translationInfoMapper.mapToDto(translationInfo);
            getTranslationQueryResponse.setTranslation(translationViewModel);
        }
        return getTranslationQueryResponse;
    }

    public GetAllTranslationsQueryResponse getAllTranslationInfo(GetAllTranslationQuery getAllTranslationQuery) {
        var validator = new GetAllTranslationQueryValidator();
        ValidationResult validationResult = validator.validate(getAllTranslationQuery);

        var response = new GetAllTranslationsQueryResponse(validationResult);
        if (response.isSuccess()) {
            var translationInfos = translationInfoRepository.getTranslationHistoryList(getAllTranslationQuery.getPageNumber(),
                    getAllTranslationQuery.getPageSize(), getAllTranslationQuery.isOrderByCreated());
            List<TranslationInfoDto> dtoList = translationInfos.stream()
                    .map(translationInfoMapper::mapToDto)
                    .toList();
            response.setTranslations(dtoList);
        }
        return response;
    }
}
