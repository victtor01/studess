package org.acme.application.usecases.users;

import org.acme.domain.models.users.User;
import org.acme.domain.values.Email;
import org.acme.domain.values.Name;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateUserUseCase {
	public User execute(CreateUserCommand createUserCommand) throws Exception {
		Email email = new Email(createUserCommand.email());
		Name firstName = new Name(createUserCommand.firstName());
		Name lastName = new Name(createUserCommand.lastName());

		User user = User.create(firstName, lastName, email);

		return user;
	}
}
