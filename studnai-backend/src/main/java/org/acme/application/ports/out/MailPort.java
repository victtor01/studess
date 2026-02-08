package org.acme.application.ports.out;

import io.smallrye.mutiny.Uni;

public interface MailPort {
    Uni<Void> sendMagicLink(String email, String link);
}