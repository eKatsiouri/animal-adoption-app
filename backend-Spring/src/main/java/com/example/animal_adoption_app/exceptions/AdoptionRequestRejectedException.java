package com.example.animal_adoption_app.exceptions;

public class AdoptionRequestRejectedException extends GenericException{
    private static final String DEFAULT_CODE = "Adoption request has been rejected";

    public AdoptionRequestRejectedException(String message) {
        super(DEFAULT_CODE, message);
    }
}
