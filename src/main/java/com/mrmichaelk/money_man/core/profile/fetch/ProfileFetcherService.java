package com.mrmichaelk.money_man.core.profile.fetch;

import com.mrmichaelk.money_man.core.profile.Profile;

public interface ProfileFetcherService {
    Profile fetch(FetchProfileRequest request) throws FailedToFetchProfileException;
}
