package org.acme.application.ports.out;

import io.smallrye.mutiny.Uni;

public interface AuthCachePort {
    Uni<Void> saveToken(String email, String token, long ttlInSeconds);
    Uni<String> getEmailByToken(String token);
    Uni<Void> invalidateToken(String token);
}