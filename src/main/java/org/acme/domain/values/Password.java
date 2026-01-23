package org.acme.domain.values;

import org.acme.application.ports.in.PasswordService;

public record Password(String hash) {

	public Password {
		if (hash == null || hash.isBlank()) {
			throw new IllegalArgumentException("Hash da senha inválido");
		}
	}

	public static Password fromPlainText(String plainPassword, PasswordService passwordService) throws Exception {
		validatePlainPassword(plainPassword);

		String hash = passwordService.hashPassword(plainPassword);

		return new Password(hash);
	}

	public static Password fromHash(String hash) {
		return new Password(hash);
	}

	public boolean matches(String plainPassword, PasswordService passwordService) throws Exception {
		return passwordService.verifyPassword(plainPassword, this.hash);
	}

	@Override
	public String toString() {
		return this.hash;
	}

	private static void validatePlainPassword(String password) {
		if (password == null || password.isBlank()) {
			throw new IllegalArgumentException("Senha obrigatória");
		}
		if (password.length() < 8) {
			throw new IllegalArgumentException(
					"Senha deve ter no mínimo 8 caracteres");
		}
	}
}
