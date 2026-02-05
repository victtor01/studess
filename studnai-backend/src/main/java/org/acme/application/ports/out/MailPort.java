package org.acme.application.ports.out;

public interface MailPort {
    void sendMagicLink(String email, String link);
}