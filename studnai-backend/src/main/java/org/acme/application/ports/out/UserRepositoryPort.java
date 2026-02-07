package org.acme.application.ports.out;

import org.acme.domain.models.users.User;

import io.smallrye.mutiny.Uni;

public interface UserRepositoryPort {
    Uni<User> findByEmail(String email);
    Uni<User> save(User user);
}