package com.karimzai.tbanktask.Application.Contracts.Infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.karimzai.tbanktask.Application.Exceptions.ServiceUnavailableException;

public interface ITranslationService {
    String translate(String text, String srcLang, String destLang) throws ServiceUnavailableException;
    boolean existLanguage(String lang) throws ServiceUnavailableException;
}
