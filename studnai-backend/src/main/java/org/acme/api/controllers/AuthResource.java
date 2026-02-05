package org.acme.api.controllers;

import java.time.Duration;
import java.util.Map;

import org.acme.api.dtos.request.AuthRequest;
import org.acme.api.utils.CookieUtil;
import org.acme.application.usecases.auth.authenticate.ValidateAuthUseCase;
import org.acme.application.usecases.auth.request.RequestLoginUseCase;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    private final ValidateAuthUseCase validateAuthUseCase;
    private final RequestLoginUseCase requestLoginUseCase;

    @Inject
    public AuthResource(ValidateAuthUseCase validateAuthUseCase, RequestLoginUseCase requestLoginUseCase) {
        this.validateAuthUseCase = validateAuthUseCase;
        this.requestLoginUseCase = requestLoginUseCase;
    }

    @POST
    @Path("/request")
    public Response request(@Valid AuthRequest request) {
        requestLoginUseCase.execute(request.email());
        
        return Response.ok(Map.of("message", "Email enviado com sucesso!")).build();
    }

    @GET
    @Path("/callback")
    public Response callback(@QueryParam("token") String token) {
        String email = validateAuthUseCase.execute(token);
        
        String sessionId = Jwt.issuer("study-platform")
            .subject(email)
            .groups("USER")
            .expiresIn(Duration.ofHours(24))
            .sign();

        NewCookie sessionCookie = CookieUtil.createSessionCookie(sessionId);

        return Response.ok(Map.of("message", "Login realizado com sucesso"))
            .cookie(sessionCookie)
            .build();
    }
}