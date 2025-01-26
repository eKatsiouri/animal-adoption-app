package com.example.animal_adoption_app.exceptions;

public class NotAuthorizedException extends GenericException{
    private static final String DEFAULT_CODE = "Not authorized";

    public NotAuthorizedException( String code, String message ) {
        super(code + DEFAULT_CODE,message);
    }


}
