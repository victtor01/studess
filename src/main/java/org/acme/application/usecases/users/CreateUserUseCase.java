package org.acme.application.usecases.users;

import java.util.jar.Attributes.Name;

import org.acme.domain.models.users.User;
import org.acme.domain.values.Email;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateUserUseCase {
	public User execute(CreateUserCommand createUserCommand) throws Exception {
		Email email = new Email(createUserCommand.email());
		Name firstName = new Name(createUserCommand.firstName());
		Name lastName = new Name(createUserCommand.lastName());

		User user = new User(firstName, lastName, email);

		return user;
	}
}
