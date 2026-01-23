package org.acme.domain.models.users;

import java.time.Instant;
import java.util.jar.Attributes.Name;

import org.acme.domain.values.Email;

public class User {
    private String id;
	private Name firstName;
	private Name lastName;
	private Email email;
    private Instant createdAt;

    public User() {}

	public User(Name firstName, Name lastName, Email email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public Name getFirstName() {
		return this.firstName;
	}

	public Name getLastName() {
		return this.lastName;
	}

	public Email getEmail() {
		return this.email;
	}

    public void setEmail(Email newEmail) {
        this.email = newEmail;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
