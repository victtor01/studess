package org.acme.infrastructure.adapters.in.services;

import org.acme.application.ports.in.PasswordService;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PasswordServiceAdapter implements PasswordService {

    @Override
    public String hashPassword(String password) {
        return BcryptUtil.bcryptHash(password);
    }

    @Override
    public boolean verifyPassword(String password, String hash) {
        return BcryptUtil.matches(password, hash);
    }
}
