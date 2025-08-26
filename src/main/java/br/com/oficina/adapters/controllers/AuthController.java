package br.com.oficina.adapters.controllers;

import br.com.oficina.adapters.controllers.dto.LoginRequest;
import br.com.oficina.adapters.controllers.dto.LoginResponse;
import br.com.oficina.usecases.auth.AutenticarUsuarioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Autenticação")
public class AuthController {

    private final AutenticarUsuarioUseCase autenticar;
    private final long ttlMillis;

    public AuthController(AutenticarUsuarioUseCase autenticar,
                          @Value("${security.jwt.ttlMillis:3600000}") long ttlMillis) {
        this.autenticar = autenticar;
        this.ttlMillis = ttlMillis;
    }

    @PostMapping(path="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Login - recebe username e password (texto puro) e retorna JWT Bearer")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        String token = autenticar.autenticar(req.username(), req.password());
        return ResponseEntity.ok(LoginResponse.bearer(token, ttlMillis));
    }
}
