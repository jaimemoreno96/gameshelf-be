package com.jaimemoreno.gameshelf.domain.user;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class User {

    private final Long id;
    private final String username;
    private final String password;
    private final Set<Role> roles;
    private final boolean enabled;

    public User(
            Long id,
            String username,
            String password,
            Set<Role> roles,
            boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = Collections.unmodifiableSet(new HashSet<>(roles));
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
