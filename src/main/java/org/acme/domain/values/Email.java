package org.acme.domain.values;

import java.util.regex.Pattern;

import org.acme.domain.exceptions.InvalidEmailException;

public class Email {
	private String address;

	private final static String EMAIL_REGEX = "^(.+)@(\\S+)$";

	public String getAddress() {
		return address;
	}

	public Email(String email) {
        if (!patternMatches(email)) {
            throw new InvalidEmailException(email);
        }

		this.address = email;
	}

	public void update(String email) throws Exception {
		this.validateEmail(email);
		this.address = email;
	}

	private void validateEmail(String value) throws Exception {
		if (!patternMatches(value)) {
			throw new InvalidEmailException("Email inválido");
		}
	}

	public static boolean patternMatches(String emailAddress) {
		return Pattern.compile(EMAIL_REGEX)
				.matcher(emailAddress)
				.matches();
	}
}
