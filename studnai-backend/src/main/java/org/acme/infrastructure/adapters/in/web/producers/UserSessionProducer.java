package org.acme.infrastructure.adapters.in.web.producers;

import org.acme.domain.models.auth.Session;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;

public class UserSessionProducer {

    @Produces
    @RequestScoped
    public Session produceSession(@Context ContainerRequestContext context) {
        // "user_session" é a chave que você usou no seu Filter: requestContext.setProperty("user_session", session);
        return (Session) context.getProperty("user_session");
    }
}