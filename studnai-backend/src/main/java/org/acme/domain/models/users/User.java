package org.acme.domain.models.users;

import java.time.Instant;
import java.util.UUID;

import org.acme.domain.values.Email;
import org.acme.domain.values.Name;

public class User {
    private final UUID id;
	private final Name firstName;
	private final Name lastName;
	private Email email;
    private Instant createdAt;

	private User(UUID id, Name firstName, Name lastName, Email email, Instant createdAt) {
        this.id = id;
        this.createdAt = createdAt;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

    public static User create(Name firstName, Name lastName, Email email) {
        return new User(UUID.randomUUID(), firstName, lastName, email, Instant.now());
    }

    public static User rehydrate(UUID id, Name firstName, Name lastName, Email email, Instant createdAt) {
        return new User(id,firstName, lastName, email,createdAt);
    }

    public UUID getId() {
        return this.id;
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
