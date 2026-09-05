package com.mrmichaelk.money_man.core.profile.create;

public class FailedToCreateProfileException extends RuntimeException {

    private FailedToCreateProfileException() {}
    
    public static FailedToCreateProfileException becauseNameCannotBeEmpty() {
        return new FailedToCreateProfileException();
    }
}
