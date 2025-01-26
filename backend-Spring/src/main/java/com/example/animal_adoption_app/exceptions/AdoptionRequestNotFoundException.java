package com.example.animal_adoption_app.exceptions;

public class AdoptionRequestNotFoundException extends GenericException {
  private static final String DEFAULT_CODE = "Adoption request not found";

  public AdoptionRequestNotFoundException(String message) {
    super(DEFAULT_CODE, message);
  }
}