package org.acme.infrastructure.adapters.in.web.filters;

import java.io.IOException;

import org.acme.application.ports.out.SessionServicePort;
import org.acme.infrastructure.adapters.in.web.security.UserSecurityContext;

import io.smallrye.mutiny.Uni;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
@Priority(Priorities.AUTHENTICATION)
public class AuthSessionFilter  {

    @Inject
    SessionServicePort sessionService;

    @Secured
    public Uni<Response> filter(ContainerRequestContext requestContext) throws IOException {
        String sessionId = this.extractSessionId(requestContext);

        if (sessionId == null) {
            return Uni.createFrom().item(unauthorizedResponse());
        }

        return sessionService.get(sessionId)
            .onItem().transform(session -> {
                
                if (session == null) {
                    return unauthorizedResponse(); // Aborta com 401
                }

                // Cenário B: Sucesso! Configura o contexto
                requestContext.setProperty("user_session", session);
                requestContext.setSecurityContext(new UserSecurityContext(session));

                // Retornar NULL significa "Pode passar, continue a requisição"
                return null; 
            });
    }

    private String extractSessionId(ContainerRequestContext requestContext) {
        Cookie sessionCookie = requestContext.getCookies().get("SESSION_ID");
        String sessionId = (sessionCookie != null) ? sessionCookie.getValue() : 
                           requestContext.getHeaderString("X-Session-ID");
        
        if (sessionId != null && !sessionId.isEmpty()) {
            return sessionId;
        }
        return null;
    }

    private Response unauthorizedResponse() {
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Sessão inválida ou expirada.")
                .build();
    }
    
}