package org.acme.infrastructure.adapters.out.database.repositories;

import org.acme.application.ports.out.UserRepositoryPort;
import org.acme.domain.models.users.User;
import org.acme.infrastructure.adapters.out.database.entities.user.UserEntity;
import org.acme.infrastructure.mappers.UserInfraMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort, PanacheRepositoryBase<UserEntity, String> {

    @Inject
    UserInfraMapper userMapper;

    @Override
    public Uni<User> findByEmail(String email) {
        return find("email", email)
                .firstResult() // Retorna Uni<UserEntity> (pode ser nulo se não achar)
                .map(userMapper::toDomain); // O Mapper deve saber lidar com null
    }

    @Override
    public Uni<User> save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        
        return Panache.withTransaction(() -> 
            persist(entity).replaceWith(entity))
            .map(userMapper::toDomain);
    }
}