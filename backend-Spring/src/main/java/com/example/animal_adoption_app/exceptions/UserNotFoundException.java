package com.example.animal_adoption_app.exceptions;

public class UserNotFoundException extends GenericException{
    private static final String DEFAULT_CODE = "User not found";

    public UserNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
