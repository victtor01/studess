package org.acme.api.controllers;

import java.time.Duration;
import java.util.Map;

import org.acme.api.dtos.request.AuthRequest;
import org.acme.application.usecases.auth.authenticate.AuthenticateUserUseCase;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    private final AuthenticateUserUseCase useCase;

    @Inject
    public AuthResource(AuthenticateUserUseCase useCase) {
        this.useCase = useCase;
    }

    @POST
    @Path("/request")
    public Response request(@Valid AuthRequest request) {
        useCase.requestLogin(request.email());
        return Response.ok().build();
    }

    @GET
    @Path("/callback")
    public Response callback(@QueryParam("token") String token) {
        String email = useCase.validateLogin(token);
        
        // Gera o JWT final para o usuário
        String jwt = Jwt.issuer("study-platform")
                .subject(email)
                .groups("USER")
                .expiresIn(Duration.ofHours(24))
                .sign();

        return Response.ok(Map.of("access_token", jwt)).build();
    }
}