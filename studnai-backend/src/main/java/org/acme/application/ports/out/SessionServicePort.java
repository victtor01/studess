package org.acme.application.ports.out;

import org.acme.domain.models.auth.Session;

public interface SessionServicePort {
    void save(Session session, long ttlSeconds);
    Session get(String sessionId);
    void invalidate(String sessionId);
}
