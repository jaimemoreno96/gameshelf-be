package com.jaimemoreno.gameshelf.application.user.service;

import com.jaimemoreno.gameshelf.application.user.command.RegisterUserCommand;
import com.jaimemoreno.gameshelf.application.user.port.in.RegisterUserUseCase;
import com.jaimemoreno.gameshelf.application.user.port.out.PasswordEncoderPort;
import com.jaimemoreno.gameshelf.application.user.port.out.UserRepository;
import com.jaimemoreno.gameshelf.domain.user.Role;
import com.jaimemoreno.gameshelf.domain.user.User;

import java.util.Set;

public class UserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(RegisterUserCommand command) {
        if (userRepository.findByUsername(command.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        String encoded = passwordEncoder.encode(command.password());
        User user = new User(null, command.username(), encoded, Set.of(Role.USER), true);
        return userRepository.save(user);
    }
}
