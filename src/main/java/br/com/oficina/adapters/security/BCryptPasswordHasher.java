package br.com.oficina.adapters.security;

import br.com.oficina.ports.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordHasher implements PasswordHasher {

    private final BCryptPasswordEncoder encoder;

    public BCryptPasswordHasher() {
        // custo 10 é um bom equilíbrio para ambiente dev; suba em produção
        this.encoder = new BCryptPasswordEncoder(10);
    }

    @Override
    public String hash(String rawPassword) {
        if (rawPassword == null) throw new IllegalArgumentException("rawPassword null");
        return encoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String hashed) {
        if (rawPassword == null || hashed == null) return false;
        return encoder.matches(rawPassword, hashed);
    }
}
