package com.nagarro.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FactoryValidator {

    private final NumericValidator numericValidator;
    private final EnglishAlphabetsValidator englishAlphabetsValidator;

    @Autowired
    public FactoryValidator(NumericValidator numericValidator, EnglishAlphabetsValidator englishAlphabetsValidator) {
        this.numericValidator = numericValidator;
        this.englishAlphabetsValidator = englishAlphabetsValidator;
    }

    public CustomValidator getValidator(String parameterType) {
        if ("Numeric".equalsIgnoreCase(parameterType)) {
            return numericValidator;
        } else if ("Alphabets".equalsIgnoreCase(parameterType)) {
            return englishAlphabetsValidator;
        }
        // Handle other cases or throw an exception for unknown types
        throw new IllegalArgumentException("Invalid parameter type: " + parameterType);
    }
}

