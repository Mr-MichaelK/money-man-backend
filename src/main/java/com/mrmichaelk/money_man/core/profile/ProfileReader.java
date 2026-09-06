package com.mrmichaelk.money_man.core.profile;

import java.util.Optional;

public interface ProfileReader {
    Optional<Profile> findById(int id);
}
