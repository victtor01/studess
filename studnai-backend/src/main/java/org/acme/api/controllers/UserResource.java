package org.acme.api.controllers;

import org.acme.api.dtos.request.CreateUserRequest;
import org.acme.api.mappers.UserMapperApi;
import org.acme.application.usecases.users.CreateUserCommand;
import org.acme.application.usecases.users.CreateUserUseCase;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	private final CreateUserUseCase createUserUseCase;

	@Inject
	public UserResource(CreateUserUseCase createUserUseCase) {
		this.createUserUseCase = createUserUseCase;
	}

	@POST()
	public String index(CreateUserRequest request) throws Exception {
		CreateUserCommand command = UserMapperApi.tCommandoCreateUserCommand(request);

		return createUserUseCase.execute(command).getEmail().getAddress();
	} 
}
