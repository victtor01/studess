package org.acme.infrastructure.mappers;

import org.acme.domain.models.users.User;
import org.acme.domain.values.Email;
import org.acme.infrastructure.adapters.out.database.entities.user.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserMapper {
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User user = new User();
        
        user.setId(entity.id);
        user.setEmail(new Email(entity.email));
        user.setCreatedAt(entity.createdAt);
        
        return user;
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;

        UserEntity entity = new UserEntity();
        
        entity.id = domain.getId();
        entity.email = domain.getEmail().getAddress();
        entity.createdAt = domain.getCreatedAt();
        
        return entity;
    }
}