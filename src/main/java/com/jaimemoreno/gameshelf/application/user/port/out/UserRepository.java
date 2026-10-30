package com.jaimemoreno.gameshelf.application.user.port.out;

import com.jaimemoreno.gameshelf.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByUsername(String username);
}
