package br.com.oficina.usecases.auth;

import br.com.oficina.domain.Usuario;
import br.com.oficina.ports.AuthTokenProvider;
import br.com.oficina.ports.PasswordHasher;
import br.com.oficina.ports.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarios;
    private final PasswordHasher hasher;
    private final AuthTokenProvider tokens;
    private final long ttlMillis;

    public AutenticarUsuarioUseCase(
            UsuarioRepository usuarios,
            PasswordHasher hasher,
            AuthTokenProvider tokens,
            @Value("${security.jwt.ttlMillis:3600000}") long ttlMillis // default 1h
    ) {
        this.usuarios = usuarios;
        this.hasher = hasher;
        this.tokens = tokens;
        this.ttlMillis = ttlMillis;
    }

    public String autenticar(String username, String password) {
        Usuario u = usuarios.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("usuário/senha inválidos"));
        if (!u.isAtivo()) throw new IllegalArgumentException("usuário desativado");
        if (!hasher.matches(password, u.getPassword())) {
            throw new IllegalArgumentException("usuário/senha inválidos");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", u.getId());
        claims.put("roleId", u.getRoleId()); // simples; mapeie para ROLE_* se quiser GrantedAuthorities
        return tokens.generateToken(u.getUsername(), claims, ttlMillis);
    }
}
