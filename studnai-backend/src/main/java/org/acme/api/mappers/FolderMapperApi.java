package org.acme.api.mappers;

import java.util.UUID;

import org.acme.api.dtos.request.CreateFolderRequest;
import org.acme.application.usecases.folders.createFolder.CreateFolderCommand;

public class FolderMapperApi {
    public static CreateFolderCommand toCommand(CreateFolderRequest request, UUID ownerId) {
        if(request == null) return null;

        return new CreateFolderCommand(
            request.name(),
            request.parentId(),
            ownerId.toString(),
            request.icon()
        );
    }
}
