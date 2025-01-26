package com.example.animal_adoption_app.exceptions;

public class ValidationException extends GenericException{
    private static final String DEFAULT_CODE = "Validation failed";

    public ValidationException(String code , String message) {
        super(code +DEFAULT_CODE, message);
    }
}
