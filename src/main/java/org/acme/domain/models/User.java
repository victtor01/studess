package org.acme.domain.models;

import org.acme.domain.values.Email;

import lombok.Data;

@Data
public class User {
	private String firstName;
	private String lastName;
	private Email email;
}
