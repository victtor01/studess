package org.acme.infrastructure.adapters.out.redis;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

import org.acme.application.ports.out.AuthCachePort;

@ApplicationScoped
public class RedisAuthAdapter implements AuthCachePort {

    private final ValueCommands<String, String> countCommands;

    public RedisAuthAdapter(RedisDataSource ds) {
        this.countCommands = ds.value(String.class);
    }

    @Override
    public void saveToken(String email, String token, long ttl) {
        countCommands.setex(token, ttl, email);
    }

    @Override
    public String getEmailByToken(String token) {
        return countCommands.get(token);
    }

    @Override
    public void invalidateToken(String token) {
        countCommands.getdel(token);
    }
}