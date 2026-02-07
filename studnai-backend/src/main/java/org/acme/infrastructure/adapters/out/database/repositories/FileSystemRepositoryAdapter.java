package org.acme.infrastructure.adapters.out.database.repositories;

import org.acme.application.ports.out.FileSystemRepositoryPort;
import org.acme.domain.models.files.Folder;
import org.acme.infrastructure.adapters.out.database.entities.files.FolderEntity;
import org.acme.infrastructure.mappers.FolderInfraMapper;

import io.quarkus.hibernate.reactive.panache.Panache; 
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class FileSystemRepositoryAdapter implements FileSystemRepositoryPort {

    @Inject
    FolderInfraMapper folderInfraMapper;

    @Override
    public Uni<Folder> saveFolder(Folder folder) {
        FolderEntity entity = folderInfraMapper.toEntity(folder);
        
        return Panache.withTransaction(() -> entity.persist().replaceWith(entity))
        .map(savedEntity -> folderInfraMapper.toDomain(savedEntity));
    }
}