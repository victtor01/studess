package org.acme.infrastructure.adapters.out.redis;

import org.acme.application.ports.out.AuthCachePort;

import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisAuthAdapter implements AuthCachePort {

    private final ReactiveValueCommands<String, String> countCommands;

    public RedisAuthAdapter(ReactiveRedisDataSource ds) {
        this.countCommands = ds.value(String.class);
    }

    @Override
    public Uni<Void> saveToken(String token, String email, long ttl) {
        return countCommands.setex(token, ttl, email);
    }

    @Override
    public Uni<String> getEmailByToken(String token) {
        return countCommands.get(token);
    }

    @Override
    public Uni<Void> invalidateToken(String token) {
        return countCommands.getdel(token)
        .replaceWithVoid();
    }
}