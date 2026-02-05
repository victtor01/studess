package org.acme.api.utils;

import java.time.Duration;

import jakarta.ws.rs.core.NewCookie;

public class CookieUtil {

    private static final String SESSION_KEY = "SESSION_ID";

    public static NewCookie createSessionCookie(String sessionId) {
        return new NewCookie.Builder(SESSION_KEY)
            .value(sessionId)
            .path("/")
            .comment("User Session ID")
            .maxAge((int) Duration.ofHours(24).toSeconds())
            .secure(false) // Coloque como true em produção (HTTPS)
            .httpOnly(true)
            .sameSite(NewCookie.SameSite.LAX)
            .build();
    }
}