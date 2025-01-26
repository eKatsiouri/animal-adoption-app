package com.example.animal_adoption_app.exceptions;

public class AnimalNotFoundException extends GenericException{
    private static final String DEFAULT_CODE = "Animal Not Found";

    public AnimalNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE,message);
    }
}
