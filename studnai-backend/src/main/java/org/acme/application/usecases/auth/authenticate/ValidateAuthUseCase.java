package org.acme.application.usecases.auth.authenticate;

import java.util.UUID;

import org.acme.application.ports.out.AuthCachePort;
import org.acme.application.ports.out.CryptographyFactoryPort;
import org.acme.application.ports.out.SessionServicePort;
import org.acme.application.ports.out.UserRepositoryPort;
import org.acme.domain.models.auth.Session;
import org.acme.domain.models.users.User;
import org.acme.domain.values.Email;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ValidateAuthUseCase {
    private final UserRepositoryPort userRepositoryPort;
    private final AuthCachePort cachePort;
    private final CryptographyFactoryPort cryptographyFactoryPort;
    private final SessionServicePort sessionServicePort;

    @Inject
    public ValidateAuthUseCase(AuthCachePort cachePort, UserRepositoryPort userRepositoryPort, CryptographyFactoryPort cryptographyFactoryPort, SessionServicePort sessionServicePort) {
        this.cachePort = cachePort;
        this.userRepositoryPort = userRepositoryPort;
        this.cryptographyFactoryPort = cryptographyFactoryPort;
        this.sessionServicePort = sessionServicePort;
    }

    @Transactional
    public String execute(String token) {
        String tokenHash = cryptographyFactoryPort.hash(token);

        String email = cachePort.getEmailByToken(tokenHash);
          
        if (email == null) {
            throw new RuntimeException("Token inválido ou expirado");
        }
        
        cachePort.invalidateToken(tokenHash);

        User currentUser = userRepositoryPort.findByEmail(email)
            .orElseGet(() -> {
                Email newEmail = new Email(email);

                User newUser = User.create(null, null, newEmail);
                
                return userRepositoryPort.save(newUser);
            });

        UUID userId = currentUser.getId();
        String sessionId = UUID.randomUUID().toString();

        long ttlInSeconds = 60 * 60 * 24 * 7;
        long expiresAt = System.currentTimeMillis() + (ttlInSeconds * 1000);

        Session session = new Session(sessionId, userId, expiresAt);

        sessionServicePort.save(session, ttlInSeconds);

        return sessionId;
    }
}