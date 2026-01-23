package org.acme.application.ports.in;

public interface PasswordService {
	String hashPassword(String plainPassword) throws Exception;
	boolean verifyPassword(String plainPassword, String hashedPassword) throws Exception;
}
