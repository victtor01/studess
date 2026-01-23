package org.acme.infrastructure.adapters.out.mail;

import org.acme.application.ports.out.MailPort;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class QuarkusMailAdapter implements MailPort {

    @Inject Mailer mailer;

    @Override
    public void sendMagicLink(String email, String link) {
        mailer.send(Mail.withText(email, "Seu Link de Acesso", "Clique aqui: " + link));
    }
}