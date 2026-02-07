package org.acme.infrastructure.adapters.in.services;

import org.acme.application.ports.out.SessionServicePort;
import org.acme.domain.models.auth.Session;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RedisSessionService implements SessionServicePort {
    
    private final ReactiveValueCommands<String, Session> redis;
    
    @Inject
    public RedisSessionService(ReactiveRedisDataSource ds) {
        this.redis = ds.value(Session.class);
    }

    @Override
    public Uni<Void> save(Session session, long ttlSeconds) {
        return redis.setex(key(session.getId()),ttlSeconds,session);
    }

    @Override
    public Uni<Session> get(String sessionId) {
        return redis.get(key(sessionId))
            .chain(session -> {
              if (session == null) {
                return Uni.createFrom().nullItem();
            }

            if (session.isExpired()) {
                return invalidate(sessionId)
                    .replaceWith(Uni.createFrom().nullItem());
            }

            return Uni.createFrom().item(session);
        });
    }

    @Override
    public Uni<Void> invalidate(String sessionId) {
        return redis.getdel(key(sessionId))
            .replaceWithVoid();
    }

    private String key(String sessionId) {
        return "session:" + sessionId;
    }
}
