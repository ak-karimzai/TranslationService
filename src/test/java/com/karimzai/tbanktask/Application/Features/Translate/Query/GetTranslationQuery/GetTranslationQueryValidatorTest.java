package com.karimzai.tbanktask.Application.Features.Translate.Query.GetTranslationQuery;

import br.com.fluentvalidator.context.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetTranslationQueryValidatorTest {
    private GetTranslationQueryValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new GetTranslationQueryValidator();
    }

    @Test
    public void testValidUUID() {
        GetTranslationQuery query = new GetTranslationQuery("123e4567-e89b-12d3-a456-426614174000");
        ValidationResult validationResult = validator.validate(query);
        assertTrue(validationResult.isValid(), "Expected UUID to be valid");
    }

    @Test
    public void testInvalidUUID() {
        GetTranslationQuery query = new GetTranslationQuery("invalid-uuid");
        ValidationResult validationResult = validator.validate(query);
        assertFalse(validationResult.isValid(), "Expected UUID to be invalid");
    }

    @Test
    public void testEmptyUUID() {
        GetTranslationQuery query = new GetTranslationQuery("");
        ValidationResult validationResult = validator.validate(query);
        assertFalse(validationResult.isValid(), "Expected empty UUID to be invalid");
    }
}