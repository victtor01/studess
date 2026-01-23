package org.acme.application.usecases.auth.authenticate;

import java.util.UUID;

import org.acme.application.ports.out.AuthCachePort;
import org.acme.application.ports.out.MailPort;
import org.acme.application.ports.out.UserRepositoryPort;
import org.acme.domain.models.users.User;
import org.acme.domain.values.Email;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthenticateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final AuthCachePort cachePort;
    private final MailPort mailPort;

    public AuthenticateUserUseCase(AuthCachePort cachePort, MailPort mailPort, UserRepositoryPort userRepositoryPort) {
        this.cachePort = cachePort;
        this.mailPort = mailPort;
        this.userRepositoryPort = userRepositoryPort;
    }

    public void requestLogin(String email) {
        String token = UUID.randomUUID().toString();

        cachePort.saveToken(email, token, 900);
        
        String link = "http://localhost:8080/auth/callback?token=" + token;
        mailPort.sendMagicLink(email, link);
    }

    public String validateLogin(String token) {
        String email = cachePort.getEmailByToken(token);
        
        if (email == null) {
            throw new RuntimeException("Token inválido ou expirado");
        }
        
        cachePort.invalidateToken(token);

        userRepositoryPort.findByEmail(email)
            .orElseGet(() -> {
                Email newEmail = new Email(email);

                User newUser = new User();
                newUser.setId(UUID.randomUUID().toString());
                newUser.setEmail(newEmail);
                newUser.setCreatedAt(java.time.Instant.now());
                
                return userRepositoryPort.save(newUser);
            });

        return email;
    }
}