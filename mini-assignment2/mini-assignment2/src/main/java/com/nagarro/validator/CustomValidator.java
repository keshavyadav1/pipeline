package com.nagarro.validator;

import org.springframework.stereotype.Component;

@Component
public interface CustomValidator {
    boolean validate(String value);
}
