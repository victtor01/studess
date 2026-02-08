package org.acme.domain.models.auth;

import java.util.UUID;

public class Session {
    public String id;
    public UUID userId;
    public long expiresAt;

    public Session() {}

    public Session(String id, UUID userId, long expiresAt) {
        this.id = id;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiresAt;
    }

    public String getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }
}
