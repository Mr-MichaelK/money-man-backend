package com.mrmichaelk.money_man.core.profile.fetch;


public class FailedToFetchProfileException extends RuntimeException {
    private FailedToFetchProfileException(String message) {
        super(message);
    }

    public static FailedToFetchProfileException becauseIdCannotBeEmpty() {
        return new FailedToFetchProfileException("The id cannot be empty");
    }

    public static FailedToFetchProfileException becuaseProfileCannotBeFound(int id) {
        return new FailedToFetchProfileException(String.format("Profile with id %d cannot be found", id));
    }
}
