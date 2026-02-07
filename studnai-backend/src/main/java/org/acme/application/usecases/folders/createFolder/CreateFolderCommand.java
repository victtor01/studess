package org.acme.application.usecases.folders.createFolder;

public record CreateFolderCommand(
    String name,
    String parentId,
    String ownerId,
    String icon
) {}