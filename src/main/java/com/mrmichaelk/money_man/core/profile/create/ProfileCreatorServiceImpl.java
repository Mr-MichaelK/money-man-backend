package com.mrmichaelk.money_man.core.profile.create;

import com.mrmichaelk.money_man.core.profile.Profile;
import com.mrmichaelk.money_man.core.profile.ProfileWriter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
public class ProfileCreatorServiceImpl implements ProfileCreatorService {

    private final ProfileWriter profileWriter;

    @Override
    public CreateProfileResponse create(CreateProfileRequest request) throws FailedToCreateProfileException {
        if (request.getName() == null || request.getName().isBlank()) {
            throw FailedToCreateProfileException.becauseNameCannotBeEmpty();
        }

        Profile insertedProfile = profileWriter.insert(Profile.builder()
                                                              .name(request.getName())
                                                              .build());

        return CreateProfileResponse.builder()
                                    .id(insertedProfile.getId())
                                    .name(insertedProfile.getName())
                                    .build();
    }

}
