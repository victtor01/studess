package org.acme.application.ports.out;

import java.util.Optional;

import org.acme.domain.models.users.User;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    User save(User user);
}