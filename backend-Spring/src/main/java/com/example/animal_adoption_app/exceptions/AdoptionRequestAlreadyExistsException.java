package com.example.animal_adoption_app.exceptions;

public class AdoptionRequestAlreadyExistsException extends GenericException {
    private static final String DEFAULT_CODE = "Adoption request pending for this animal";

    public AdoptionRequestAlreadyExistsException(String message) {
        super(DEFAULT_CODE, message);
    }

}
