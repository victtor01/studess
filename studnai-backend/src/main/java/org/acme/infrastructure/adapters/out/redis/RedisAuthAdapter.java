package org.acme.infrastructure.adapters.out.redis;

import org.acme.application.ports.out.AuthCachePort;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RedisAuthAdapter implements AuthCachePort {

    private final ValueCommands<String, String> countCommands;

    public RedisAuthAdapter(RedisDataSource ds) {
        this.countCommands = ds.value(String.class);
    }

    @Override
    public void saveToken(String token, String email, long ttl) {
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