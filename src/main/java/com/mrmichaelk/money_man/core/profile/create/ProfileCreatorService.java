package com.mrmichaelk.money_man.core.profile.create;

public interface ProfileCreatorService {
    CreateProfileResponse create(CreateProfileRequest request) throws FailedToCreateProfileException;
}
