package org.acme.application.ports.out;

public interface CryptographyFactoryPort {
    String hash(String content);
}
