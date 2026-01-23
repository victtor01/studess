package org.acme.domain.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super("Email inválido: " + email);
    }
}