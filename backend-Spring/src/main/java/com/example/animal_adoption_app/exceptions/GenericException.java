package com.example.animal_adoption_app.exceptions;

import lombok.Getter;

@Getter
public class GenericException extends Exception {
    private final String code;

    public GenericException(String code , String message) {
        super(message);
        this.code = code;
    }
}
