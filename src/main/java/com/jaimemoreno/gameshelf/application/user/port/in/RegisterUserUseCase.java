package com.jaimemoreno.gameshelf.application.user.port.in;

import com.jaimemoreno.gameshelf.application.user.command.RegisterUserCommand;
import com.jaimemoreno.gameshelf.domain.user.User;

public interface RegisterUserUseCase {
    User register(RegisterUserCommand command);
}
