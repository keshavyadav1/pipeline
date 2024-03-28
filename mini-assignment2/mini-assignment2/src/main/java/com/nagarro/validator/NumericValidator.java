package com.nagarro.validator;

import org.springframework.stereotype.Component;

@Component
public class NumericValidator implements CustomValidator {

    private NumericValidator() {
        
    }

    public static NumericValidator getInstance() {
        return NumericValidatorHolder.INSTANCE;
    }

    @Override
    public boolean validate(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static class NumericValidatorHolder {
        private static final NumericValidator INSTANCE = new NumericValidator();
    }
}
