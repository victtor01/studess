package org.acme.domain.values;

import java.util.regex.Pattern;

public class Email {
	private String address;

	private final static String EMAIL_REGEX = "^(.+)@(\\S+)$";

	public String getAddress() {
		return address;
	}

	public Email(String email) throws Exception {
		this.validateEmail(email);
		this.address = email;
	}

	public void update(String email) throws Exception {
		this.validateEmail(email);
		this.address = email;
	}

	private void validateEmail(String value) throws Exception {
		if (!patternMatches(value)) {
			throw new Exception("Email inválido");
		}
	}

	public static boolean patternMatches(String emailAddress) {
		return Pattern.compile(EMAIL_REGEX)
				.matcher(emailAddress)
				.matches();
	}
}
