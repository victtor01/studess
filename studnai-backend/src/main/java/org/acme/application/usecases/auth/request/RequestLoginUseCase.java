package org.acme.application.usecases.auth.request;

import java.util.UUID;

import org.acme.application.ports.out.AuthCachePort;
import org.acme.application.ports.out.CryptographyFactoryPort;
import org.acme.application.ports.out.MailPort;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RequestLoginUseCase {
    private final AuthCachePort cachePort;
    private final MailPort mailPort;
    private final CryptographyFactoryPort cryptographyFactoryPort;

    public RequestLoginUseCase(AuthCachePort cachePort, MailPort mailPort, CryptographyFactoryPort cryptographyFactoryPort) {
        this.cachePort = cachePort;
        this.mailPort = mailPort;
        this.cryptographyFactoryPort = cryptographyFactoryPort;
    }

    public void execute(String email) {
        String token = UUID.randomUUID().toString();

        String hashToken = cryptographyFactoryPort.hash(token);

        cachePort.saveToken(hashToken, email, 900);
        
        String link = "http://localhost:4200/validate?token=" + token;
        mailPort.sendMagicLink(email, link);
    }
}
