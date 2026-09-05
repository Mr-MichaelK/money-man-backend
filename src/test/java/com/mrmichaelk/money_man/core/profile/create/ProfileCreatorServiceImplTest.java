package com.mrmichaelk.money_man.core.profile.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mrmichaelk.money_man.core.profile.Profile;
import com.mrmichaelk.money_man.core.profile.ProfileWriter;

@ExtendWith(MockitoExtension.class)
class ProfileCreatorServiceImplTest {
    
    private static final String BLANK_STRING = "";
    private static final int ID = ThreadLocalRandom.current().nextInt(100);
    private static final String NAME = UUID.randomUUID().toString();

    @Mock 
    private ProfileWriter profileWriter;

    @InjectMocks 
    private ProfileCreatorServiceImpl profileCreatorService;

    @Test 
    @DisplayName("given that a request is received to create a profile with a missing name then the system should reject the request")
    void given_that_a_request_is_received_to_create_a_profile_with_a_missing_name_then_the_system_should_reject_the_request() {
        CreateProfileRequest request = getCreateProfileRequest();
        request.setName(null);

        assertThrows(FailedToCreateProfileException.class, () -> profileCreatorService.create(request));
    }

    @Test 
    @DisplayName("given that a request is received to creata a profile with a blank name then the system should reject the request")
    void given_that_a_request_is_received_to_create_a_profile_with_a_blank_name_then_the_system_should_reject_the_request() {
        CreateProfileRequest request = getCreateProfileRequest();
        request.setName(BLANK_STRING);

        assertThrows(FailedToCreateProfileException.class, () -> profileCreatorService.create(request));
    }

    @Test
    @DisplayName("given that a request is received to create a profile then the system should creata a new profile")
    void given_that_a_request_is_received_to_create_a_profile_then_the_system_should_create_a_new_profile() {
        CreateProfileRequest request = getCreateProfileRequest();

        when(profileWriter.insert(getProfile())).thenReturn(getCreatedProfile());

        CreateProfileResponse response = profileCreatorService.create(request);

        assertEquals(getExpectedCreateProfileResponse(), response);
    }

    private CreateProfileRequest getCreateProfileRequest() {
        return CreateProfileRequest.builder()
                                   .name(NAME)
                                   .build();
    }

    private CreateProfileResponse getExpectedCreateProfileResponse() {
        return CreateProfileResponse.builder()
                                    .id(ID)
                                    .name(NAME)
                                    .build();
    }

    private Profile getProfile() {
        return Profile.builder()
                      .name(NAME)
                      .build();
    }

    private Profile getCreatedProfile() {
        return Profile.builder()
                      .id(ID)
                      .name(NAME)
                      .build();
    }
}
