package com.jaimemoreno.gameshelf.adapter.out.persistence.user;

import com.jaimemoreno.gameshelf.application.user.port.out.UserRepository;
import com.jaimemoreno.gameshelf.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserRepository {

    private final UserJpaRepository repository;

    public UserPersistenceAdapter(UserJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return toDomain(repository.save(toEntity(user)));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username).map(UserPersistenceAdapter::toDomain);
    }

    private static UserJpaEntity toEntity(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                EnumSet.copyOf(user.getRoles()),
                user.isEnabled());
    }

    private static User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRoles(),
                entity.isEnabled());
    }
}
