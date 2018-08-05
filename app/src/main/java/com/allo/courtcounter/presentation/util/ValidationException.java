package com.allo.courtcounter.presentation.util;

public class ValidationException extends Exception {

    public ValidationException() {
        super();
    }

    public ValidationException(String errorMessage) {
        super(errorMessage);
    }
}
