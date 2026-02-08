package org.acme.application.usecases.auth.authenticate;

import java.util.UUID;

import org.acme.application.ports.out.AuthCachePort;
import org.acme.application.ports.out.CryptographyFactoryPort;
import org.acme.application.ports.out.SessionServicePort;
import org.acme.application.ports.out.UserRepositoryPort;
import org.acme.domain.models.auth.Session;
import org.acme.domain.models.users.User;
import org.acme.domain.values.Email;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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

    @WithTransaction
    public Uni<String> execute(String token) {
        String tokenHash = cryptographyFactoryPort.hash(token);

        return cachePort.getEmailByToken(tokenHash)
            .onItem().ifNull().failWith(new RuntimeException("Token inválido ou expirado!"))
            .call(email -> cachePort.invalidateToken(tokenHash))
            .chain(email -> userRepositoryPort.findByEmail(email)
                .onItem().ifNull().switchTo(() -> {
                    Email newEmail = new Email(email);
                    User newUser = User.create(null, null, newEmail);
                    
                    return userRepositoryPort.save(newUser);
                }))
            .chain(user -> {
                UUID userId = user.getId();
                String sessionId = UUID.randomUUID().toString();

                long ttlInSeconds = 60 * 60 * 24 * 7;
                long expiresAt = System.currentTimeMillis() + (ttlInSeconds * 1000);

                Session session = new Session(sessionId, userId, expiresAt);

                return sessionServicePort.save(session, ttlInSeconds)
                        .replaceWith(sessionId);
            });
    }
}