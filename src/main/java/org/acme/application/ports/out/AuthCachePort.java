package org.acme.application.ports.out;

public interface AuthCachePort {
    void saveToken(String email, String token, long ttlInSeconds);
    String getEmailByToken(String token);
    void invalidateToken(String token);
}