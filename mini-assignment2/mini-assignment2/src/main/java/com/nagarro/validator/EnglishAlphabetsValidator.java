package com.nagarro.validator;

import org.springframework.stereotype.Component;

@Component
public class EnglishAlphabetsValidator implements CustomValidator {

    private EnglishAlphabetsValidator() {
        // private constructor to enforce singleton pattern
    }

    public static EnglishAlphabetsValidator getInstance() {
        return EnglishAlphabetsValidatorHolder.INSTANCE;
    }

    @Override
    public boolean validate(String value) {
        return value.matches("^[a-zA-Z]*$");
    }

    private static class EnglishAlphabetsValidatorHolder {
        private static final EnglishAlphabetsValidator INSTANCE = new EnglishAlphabetsValidator();
    }
}
