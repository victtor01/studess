package org.acme.application.usecases.folders.createFolder;

import java.time.Instant;

import org.acme.application.ports.out.FileSystemRepositoryPort;
import org.acme.domain.models.files.Folder;
import org.acme.domain.utils.IdGenerator;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateFolderUseCase {
   private final FileSystemRepositoryPort repository;
    private final IdGenerator idGenerator;

    public CreateFolderUseCase(FileSystemRepositoryPort repository, IdGenerator idGenerator) {
        this.repository = repository;
        this.idGenerator = idGenerator;
    }

    public Uni<Folder> execute(CreateFolderCommand command) {
        if (command.name() == null || command.name().isBlank()) {
            return Uni.createFrom().failure(new IllegalArgumentException("O nome é obrigatório.")); 
        }
            
        String generatedId = String.valueOf(idGenerator.nextId());

        Folder newFolder = new Folder(
            generatedId,
            command.name(),
            command.ownerId(),
            command.parentId(),
            Instant.now(),
            command.icon(),
            null, 
            null 
        );

        return repository.saveFolder(newFolder);
    }
}   
