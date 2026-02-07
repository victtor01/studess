package org.acme.infrastructure.adapters.in.web.security;

import java.security.Principal;

import org.acme.domain.models.auth.Session;

import jakarta.ws.rs.core.SecurityContext;

public class UserSecurityContext implements SecurityContext {
    private final Session session;

    public UserSecurityContext(Session session) {
        this.session = session;
    }

    @Override
    public Principal getUserPrincipal() {
        return () -> session.getUserId().toString(); 
    }

    @Override
    public boolean isUserInRole(String role) { return true; } // Lógica de roles aqui
    @Override
    public boolean isSecure() { return false; }
    @Override
    public String getAuthenticationScheme() { return "SESSION_ID"; }
}