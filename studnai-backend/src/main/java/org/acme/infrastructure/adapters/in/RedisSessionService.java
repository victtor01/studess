package org.acme.infrastructure.adapters.in;

import org.acme.application.ports.out.SessionServicePort;
import org.acme.domain.models.auth.Session;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RedisSessionService implements SessionServicePort {
    
    private final ValueCommands<String, Session> redis;
    
    @Inject
    public RedisSessionService(RedisDataSource ds) {
        this.redis = ds.value(Session.class);
    }

    @Override
    public void save(Session session, long ttlSeconds) {
        redis.setex(
            key(session.getId()),
            ttlSeconds,
            session
        );
    }

    @Override
    public Session get(String sessionId) {
        Session session = redis.get(key(sessionId));

        if (session == null) {
            return null;
        }

        if (session.isExpired()) {
            invalidate(sessionId);
            return null;
        }

        return session;
    }

    @Override
    public void invalidate(String sessionId) {
        redis.getdel(key(sessionId));
    }

        private String key(String sessionId) {
        return "session:" + sessionId;
    }
}
