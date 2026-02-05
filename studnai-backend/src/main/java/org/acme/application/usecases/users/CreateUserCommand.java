package org.acme.application.usecases.users;

public record CreateUserCommand(
	String firstName,
	String lastName,
	String email,
	String password
) {};
