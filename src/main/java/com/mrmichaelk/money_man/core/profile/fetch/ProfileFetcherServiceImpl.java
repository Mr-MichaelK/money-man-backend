package com.mrmichaelk.money_man.core.profile.fetch;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mrmichaelk.money_man.core.profile.Profile;
import com.mrmichaelk.money_man.core.profile.ProfileReader;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ProfileFetcherServiceImpl implements ProfileFetcherService {

    private final ProfileReader profileReader;

    @Override
    public Profile fetch(FetchProfileRequest request) throws FailedToFetchProfileException {
        if (request.getId() == null) {
            throw FailedToFetchProfileException.becauseIdCannotBeEmpty();
        }

        Optional<Profile> profile = profileReader.findById(request.getId());

        if (profile.isEmpty()) {
            throw FailedToFetchProfileException.becuaseProfileCannotBeFound(request.getId());
        }

        return profile.get();
    }
}
