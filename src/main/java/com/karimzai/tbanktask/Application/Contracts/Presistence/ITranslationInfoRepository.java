package com.karimzai.tbanktask.Application.Contracts.Presistence;

import com.karimzai.tbanktask.Domain.Entities.TranslationInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITranslationInfoRepository {
    void saveTranslationInfo(TranslationInfo translationInfo);
    Optional<TranslationInfo> getTranslationInfo(UUID id);
    List<TranslationInfo> getTranslationHistoryList(int pageNumber, int pageSize, boolean orderByCreation);
}
