package com.karimzai.tbanktask.Application.Features.Translate.Query.GetAllTranslationQuery;

import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetAllTranslationQueryValidatorTest {
    private GetAllTranslationQueryValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new GetAllTranslationQueryValidator();
    }

    @Test
    public void testValidQuery() {
        GetAllTranslationQuery query = new GetAllTranslationQuery(1, 10, true);
        ValidationResult validationResult = validator.validate(query);
        assertTrue(validationResult.isValid(), "Expected page number to be valid");
    }

    @Test
    public void testInvalidPageNumber() {
        GetAllTranslationQuery query = new GetAllTranslationQuery(0, 10, true);
        ValidationResult validationResult = validator.validate(query);
        assertFalse(validationResult.isValid(), "Expected page number to be invalid");
    }

    @Test
    public void testInvalidPageSizeTooSmall() {
        GetAllTranslationQuery query = new GetAllTranslationQuery(1, 0, true);
        ValidationResult validationResult = validator.validate(query);
        assertFalse(validationResult.isValid(), "Expected page size to be invalid (too small)");
    }

    @Test
    public void testInvalidPageSizeTooLarge() {
        GetAllTranslationQuery query = new GetAllTranslationQuery(1, 21, true);
        ValidationResult validationResult = validator.validate(query);
        assertFalse(validationResult.isValid(), "Expected page size to be invalid (too large)");
    }
}