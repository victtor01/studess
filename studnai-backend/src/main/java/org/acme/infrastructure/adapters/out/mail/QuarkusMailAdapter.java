package org.acme.infrastructure.adapters.out.mail;

import org.acme.application.ports.out.MailPort;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class QuarkusMailAdapter implements MailPort {

    @Inject ReactiveMailer mailer;

    @Override
    public Uni<Void> sendMagicLink(String email, String link) {
        return mailer.send(Mail.withText(email, "Seu Link de Acesso", "Clique aqui: " + link));
    }
}