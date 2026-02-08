package org.acme.infrastructure.adapters.in.web.filters;

import org.acme.application.ports.out.SessionServicePort;
import org.acme.infrastructure.adapters.in.web.security.UserSecurityContext;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class AuthSessionFilter  { // Removi @Provider e @Priority (vão para o método)

    @Inject
    SessionServicePort sessionService;

    @Secured 
    @ServerRequestFilter(priority = Priorities.AUTHENTICATION)
    public Uni<Response> filter(ContainerRequestContext requestContext) {
        String sessionId = this.extractSessionId(requestContext);

        System.out.println("DEBUG: Session ID recebido: " + sessionId);

        if (sessionId == null) {
            System.out.println("DEBUG: Falha - ID nulo");
            return Uni.createFrom().item(unauthorizedResponse());
        }

        return sessionService.get(sessionId)
            .map(session -> { // Use .map pois não há IO bloqueante aqui dentro
                System.out.println("DEBUG: Objeto Session do Redis: " + session);
                // Se o Redis retornou null ou vazio
                if (session == null) {
                    System.out.println("DEBUG: Falha - Redis retornou null");
                    return unauthorizedResponse(); 
                }

                // Injeta a sessão no contexto para o Producer pegar depois
                requestContext.setProperty("user_session", session);
                requestContext.setSecurityContext(new UserSecurityContext(session));

                // Retornar NULL significa "Siga em frente, pode chamar o Controller"
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