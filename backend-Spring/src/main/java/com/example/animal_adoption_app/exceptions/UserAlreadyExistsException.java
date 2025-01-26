package com.example.animal_adoption_app.exceptions;

public class UserAlreadyExistsException extends GenericException{
    private static final String DEFAULT_CODE = "AlreadyExists";

    public UserAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE , message);
    }
}
