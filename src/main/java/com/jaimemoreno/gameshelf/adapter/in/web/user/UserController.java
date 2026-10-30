package com.jaimemoreno.gameshelf.adapter.in.web.user;

import com.jaimemoreno.gameshelf.application.user.command.RegisterUserCommand;
import com.jaimemoreno.gameshelf.application.user.port.in.RegisterUserUseCase;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(request.username(), request.password());
        return UserResponse.from(registerUserUseCase.register(command));
    }
}
