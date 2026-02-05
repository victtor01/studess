package org.acme.api.mappers;

import org.acme.api.dtos.request.CreateUserRequest;
import org.acme.application.usecases.users.CreateUserCommand;

public class UserMapperApi {
	public static CreateUserCommand tCommandoCreateUserCommand(CreateUserRequest request) {
		return new CreateUserCommand(request.firstName(), request.lastName(), request.email(), request.password());
	}
}
