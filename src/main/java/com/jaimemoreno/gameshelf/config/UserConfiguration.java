package com.jaimemoreno.gameshelf.config;

import com.jaimemoreno.gameshelf.application.user.port.out.PasswordEncoderPort;
import com.jaimemoreno.gameshelf.application.user.port.out.UserRepository;
import com.jaimemoreno.gameshelf.application.user.service.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    UserService userService(UserRepository userRepository, PasswordEncoderPort passwordEncoder) {
        return new UserService(userRepository, passwordEncoder);
    }
}
