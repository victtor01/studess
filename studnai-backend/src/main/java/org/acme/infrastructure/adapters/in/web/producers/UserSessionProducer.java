package org.acme.infrastructure.adapters.in.web.producers;

import org.acme.domain.models.auth.Session;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;

public class UserSessionProducer {

    @Produces
    @RequestScoped
    public Session produceSession(@Context ContainerRequestContext context) {
        Object sessionObj = context.getProperty("user_session");

        if (sessionObj instanceof Session session) {
            return session;
        }

        throw new WebApplicationException("Sessão de usuário não encontrada.", 401);
    }
}