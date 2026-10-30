package com.jaimemoreno.gameshelf.adapter.out.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.jaimemoreno.gameshelf.application.user.port.out.PasswordEncoderPort;

public class PasswordEncoderAdapter implements PasswordEncoderPort {

    private final PasswordEncoder delegate;

    public PasswordEncoderAdapter(PasswordEncoder delegate) {
        this.delegate = delegate;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return delegate.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return delegate.matches(rawPassword, encodedPassword);
    }
}
