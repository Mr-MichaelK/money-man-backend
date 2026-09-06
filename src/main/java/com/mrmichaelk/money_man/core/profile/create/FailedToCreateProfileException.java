package com.mrmichaelk.money_man.core.profile.create;

public class FailedToCreateProfileException extends RuntimeException {

    private FailedToCreateProfileException(String message) {
        super(message);
    }
    
    public static FailedToCreateProfileException becauseNameCannotBeEmpty() {
        return new FailedToCreateProfileException("The name cannot be empty");
    }
}
