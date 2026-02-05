package org.acme.infrastructure.mappers;

import java.util.Optional;

import org.acme.domain.models.users.User;
import org.acme.domain.values.Email;
import org.acme.domain.values.Name;
import org.acme.infrastructure.adapters.out.database.entities.user.UserEntity;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserInfraMapper {
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        
        User user = User.rehydrate(
            entity.id,
            Optional.ofNullable(entity.firstName)
                .map(Name::new) 
                .orElse(null),
            Optional.ofNullable(entity.lastName)
                .map(Name::new) 
                .orElse(null),
            new Email(entity.email), 
            entity.createdAt
        );
        
        return user;
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        
        entity.id = domain.getId();
        entity.email = domain.getEmail().getAddress();

        entity.firstName = Optional.ofNullable(domain.getFirstName())
            .map(Name::value)
            .orElse(null);
        
        entity.lastName = Optional.ofNullable(domain.getLastName())
            .map(Name::value)
            .orElse(null);

        entity.createdAt = domain.getCreatedAt();
        
        return entity;
    }
}