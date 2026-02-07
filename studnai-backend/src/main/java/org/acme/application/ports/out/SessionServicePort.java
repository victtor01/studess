package org.acme.application.ports.out;

import org.acme.domain.models.auth.Session;

import io.smallrye.mutiny.Uni;

public interface SessionServicePort {
    Uni<Void> save(Session session, long ttlSeconds);
    Uni<Session> get(String sessionId);
    Uni<Void> invalidate(String sessionId);
}
