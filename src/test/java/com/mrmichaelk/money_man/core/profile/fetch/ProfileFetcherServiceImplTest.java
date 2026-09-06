package com.mrmichaelk.money_man.core.profile.fetch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrmichaelk.money_man.core.profile.Profile;
import com.mrmichaelk.money_man.core.profile.ProfileReader;

@ExtendWith(MockitoExtension.class)
class ProfileFetcherServiceImplTest {

    private static final int ID = ThreadLocalRandom.current().nextInt(100);
    private static final String NAME = UUID.randomUUID().toString();

    @Mock 
    private ProfileReader profileReader;

    @InjectMocks 
    private ProfileFetcherServiceImpl profileFetcherService;

    @Test 
    @DisplayName("given that a request is received to fetch a profile with a missing id then the system should reject the request")
    void given_that_a_request_is_received_to_fetch_a_profile_with_a_missing_id_then_the_system_should_reject_the_request() {
        FetchProfileRequest request = getFetchProfileRequest();
        request.setId(null);

        assertThrows(FailedToFetchProfileException.class, () -> profileFetcherService.fetch(request));
    }

    @Test 
    @DisplayName("given that a request is received to fetch a profile with an id that does not exist then the system should reject the request")
    void given_that_a_request_is_received_to_fetch_a_profile_with_an_id_that_does_not_exist_then_the_system_should_reject_the_request() {
        FetchProfileRequest request = getFetchProfileRequest();

        when(profileReader.findById(ID)).thenReturn(Optional.empty());

        assertThrows(FailedToFetchProfileException.class, () -> profileFetcherService.fetch(request));
    }

    @Test
    @DisplayName("given that a request is received to fetch a profile then the system should fetch the profile")
    void given_that_a_request_is_received_to_fetch_a_profile_then_the_system_should_fetch_the_profile() {
        FetchProfileRequest request = getFetchProfileRequest();

        when(profileReader.findById(ID)).thenReturn(Optional.of(getExpectedProfile()));

        Profile response = profileFetcherService.fetch(request);

        assertEquals(getExpectedProfile(), response);
    }

    private FetchProfileRequest getFetchProfileRequest() {
        return FetchProfileRequest.builder()
                                  .id(ID)
                                  .build();
    }

    private Profile getExpectedProfile() {
        return Profile.builder()
                      .id(ID)
                      .name(NAME)
                      .build();
    }
}
