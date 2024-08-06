package com.karimzai.tbanktask.Application.Features.Translate.Command.TranslateTextCommand;

import br.com.fluentvalidator.context.ValidationResult;
import com.karimzai.tbanktask.Application.Contracts.Infrastructure.ITranslationService;
import com.karimzai.tbanktask.Application.Exceptions.ServiceUnavailableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class TranslateTextCommandValidatorTest {
    private AutoCloseable autoCloseable;
    @Mock
    private ITranslationService translationService;

    @InjectMocks
    private TranslateTextCommandValidator validator;

    private TranslateTextCommand validCommand;
    private TranslateTextCommand invalidCommand;

    @BeforeEach
    void setUp() throws ServiceUnavailableException {
        autoCloseable = MockitoAnnotations.openMocks(this);
        validCommand = new TranslateTextCommand("en", "ru", "Hello, world!", "192.168.1.1");

        invalidCommand = new TranslateTextCommand("en", "en", "Hello, world!", "192.168.1.1");

        when(translationService.existLanguage("en")).thenReturn(true);
        when(translationService.existLanguage("ru")).thenReturn(true);
        when(translationService.existLanguage("es")).thenReturn(true);

        validator = new TranslateTextCommandValidator(translationService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testValidCommand() {
        validCommand = new TranslateTextCommand("en", "ru", "Hello, world!", "192.168.1.1");

        ValidationResult validationResult = validator.validate(validCommand);
        assertTrue(validationResult.isValid(), "Valid command should pass validation.");
    }

    @Test
    void testSourceAndDestinationLanguagesAreSame() {
        ValidationResult validationResult = validator.validate(invalidCommand);
        assertFalse(validationResult.isValid(), "Command with the same source and destination languages should fail validation.");
    }

//    @Test
//    void testUnsupportedSourceLanguage() throws ServiceUnavailableException {
//        TranslateTextCommand command = new TranslateTextCommand("unsupported", "fr", "Hello, world!", "192.168.1.1");
//
//        when(translationService.existLanguage("unsupported")).thenReturn(false);
//
//        ValidationResult validationResult = validator.validate(command);
//        assertFalse(validationResult.isValid(), "Command with an unsupported source language should fail validation.");
//    }

//    @Test
//    void testUnsupportedDestinationLanguage() throws ServiceUnavailableException {
//        TranslateTextCommand command = new TranslateTextCommand("en", "unsupported", "Hello, world!", "192.168.1.1");
//
//        when(translationService.existLanguage("unsupported")).thenReturn(false);
//
//        ValidationResult validationResult = validator.validate(command);
//        assertFalse(validationResult.isValid(), "Command with an unsupported destination language should fail validation.");
//    }

    @Test
    void testEmptyText() {
        TranslateTextCommand command = new TranslateTextCommand("en", "fr", "", "192.168.1.1");

        ValidationResult validationResult = validator.validate(command);
        assertFalse(validationResult.isValid(), "Command with empty text should fail validation.");
    }

    @Test
    void testTooLongText() {
        String longText = "x".repeat(257);
        TranslateTextCommand command = new TranslateTextCommand("en", "fr", longText, "192.168.1.1");

        ValidationResult validationResult = validator.validate(command);
        assertFalse(validationResult.isValid(), "Command with too long text should fail validation.");
    }

    @Test
    void testValidEdgeCaseTextLength() throws ServiceUnavailableException {
        String edgeText = "x".repeat(256);
        TranslateTextCommand command = new TranslateTextCommand("en", "fr", edgeText, "192.168.1.1");

        when(translationService.existLanguage("fr")).thenReturn(true);

        ValidationResult validationResult = validator.validate(command);
        assertTrue(validationResult.isValid(), "Command with 256 character text should pass validation.");
    }
}