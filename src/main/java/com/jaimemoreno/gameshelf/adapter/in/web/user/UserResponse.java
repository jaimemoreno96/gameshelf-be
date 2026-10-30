package com.jaimemoreno.gameshelf.adapter.in.web.user;

import com.jaimemoreno.gameshelf.domain.user.Role;
import com.jaimemoreno.gameshelf.domain.user.User;

import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        Set<Role> roles,
        boolean enabled) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles(),
                user.isEnabled());
    }
}
