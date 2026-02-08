package org.acme.application.usecases.auth.request;

import java.util.UUID;

import org.acme.application.ports.out.AuthCachePort;
import org.acme.application.ports.out.CryptographyFactoryPort;
import org.acme.application.ports.out.MailPort;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RequestLoginUseCase {
    private final AuthCachePort cachePort;
    private final MailPort mailPort;
    private final CryptographyFactoryPort cryptographyFactoryPort;

    @Inject
    public RequestLoginUseCase(AuthCachePort cachePort, MailPort mailPort, CryptographyFactoryPort cryptographyFactoryPort) {
        this.cachePort = cachePort;
        this.mailPort = mailPort;
        this.cryptographyFactoryPort = cryptographyFactoryPort;
    }

    public Uni<Void> execute(String email) {
        String token = UUID.randomUUID().toString();
        String hashToken = cryptographyFactoryPort.hash(token);
        String link = "http://localhost:4200/validate?token=" + token;
        
        return cachePort.saveToken(hashToken, email, 900) 
            .chain(() -> mailPort.sendMagicLink(email, link));
    }
}
