package org.acme.infrastructure.adapters.out.database.repositories;

import java.util.Optional;

import org.acme.application.ports.out.UserRepositoryPort;
import org.acme.domain.models.users.User;
import org.acme.infrastructure.adapters.out.database.entities.user.UserEntity;
import org.acme.infrastructure.mappers.UserInfraMapper;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort, PanacheRepositoryBase<UserEntity, String> {

    @Inject
    UserInfraMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return find("email", email)
                .firstResultOptional()
                .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        // persist é do Panache. Se o ID já existir, ele faz o merge se necessário
        persist(entity);
        return userMapper.toDomain(entity);
    }
}