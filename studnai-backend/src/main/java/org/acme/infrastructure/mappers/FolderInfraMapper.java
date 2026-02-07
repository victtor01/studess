package org.acme.infrastructure.mappers;

import java.util.UUID;

import org.acme.domain.models.files.Folder;
import org.acme.infrastructure.adapters.out.database.entities.files.FolderEntity;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FolderInfraMapper {

    public FolderEntity toEntity(Folder domain) {
        if (domain == null) return null;

        FolderEntity entity = new FolderEntity();
        entity.id = domain.getId();
        entity.name = domain.getName();
        entity.icon = domain.getIcon();
        entity.metadata = domain.getMetadata();
        entity.createdAt = domain.getCreatedAt();
        
        if (domain.getOwnerId() != null) {
            entity.ownerId = UUID.fromString(domain.getOwnerId());
        }

        if (domain.getParentId().isPresent()) {
            entity.parentId = domain.getParentId().get();
        }

        return entity;
    }

    public Folder toDomain(FolderEntity entity) {
        if (entity == null) return null;

        return new Folder(
            entity.id,
            entity.name,
            entity.owner != null ? entity.owner.id.toString() : null,
            entity.parent != null ? entity.parent.id : null,
            entity.createdAt,
            entity.icon,
            null, 
            entity.metadata
        );
    }
}