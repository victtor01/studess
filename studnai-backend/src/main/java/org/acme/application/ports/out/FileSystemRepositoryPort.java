package org.acme.application.ports.out;

import org.acme.domain.models.files.Folder;

import io.smallrye.mutiny.Uni;

public interface FileSystemRepositoryPort {
    Uni<Folder> saveFolder(Folder folder);
}