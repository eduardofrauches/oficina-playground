package br.com.oficina.adapters.security;

import br.com.oficina.ports.AuthTokenProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthTokenProvider implements AuthTokenProvider {

    private final Algorithm algorithm;
    private final String issuer;

    public JwtAuthTokenProvider(
            @Value("${security.jwt.secret:dev-secret-change-me}") String secret,
            @Value("${security.jwt.issuer:oficina}") String issuer
    ) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
    }

    @Override
    public String generateToken(String subject, Map<String, Object> claims, long ttlMillis) {
        long now = System.currentTimeMillis();
        var builder = JWT.create()
                .withIssuer(issuer)
                .withSubject(subject)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + ttlMillis));
        if (claims != null) {
            for (var e : claims.entrySet()) {
                Object v = e.getValue();
                if (v instanceof String) builder.withClaim(e.getKey(), (String) v);
                else if (v instanceof Integer) builder.withClaim(e.getKey(), (Integer) v);
                else if (v instanceof Long) builder.withClaim(e.getKey(), (Long) v);
                else if (v instanceof Boolean) builder.withClaim(e.getKey(), (Boolean) v);
                else builder.withClaim(e.getKey(), String.valueOf(v));
            }
        }
        return builder.sign(algorithm);
    }

    @Override
    public Map<String, Object> validateAndGetClaims(String token) {
        DecodedJWT jwt = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        Map<String, Object> map = new HashMap<>();
        map.put("sub", jwt.getSubject());
        jwt.getClaims().forEach((k, c) -> map.put(k, c.as(Object.class)));
        return map;
    }
}
