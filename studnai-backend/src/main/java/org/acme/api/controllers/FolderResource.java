package org.acme.api.controllers;

import java.util.UUID;

import org.acme.api.dtos.request.CreateFolderRequest;
import org.acme.api.mappers.FolderMapperApi;
import org.acme.application.usecases.folders.createFolder.CreateFolderCommand;
import org.acme.application.usecases.folders.createFolder.CreateFolderUseCase;
import org.acme.domain.models.auth.Session;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@Path("/folders")
public class FolderResource {
    private final CreateFolderUseCase createFolderUseCase;
    private final Session session;

    @Inject
    public FolderResource(CreateFolderUseCase createFolderUseCase, Session context) {
        this.createFolderUseCase = createFolderUseCase;
        this.session = context;
    }

    @POST
    public Uni<Response> create(@Valid CreateFolderRequest request) {
        UUID ownerId = session.getUserId();

        CreateFolderCommand command = FolderMapperApi
            .toCommand(request, ownerId);
        
        return createFolderUseCase.execute(command)
            .map(folder -> Response.status(Response.Status.CREATED).entity(folder).build())
            .onFailure().transform(err -> new WebApplicationException(err.getMessage(), 400));
    }
}
