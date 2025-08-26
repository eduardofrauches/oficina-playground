package br.com.oficina.ports;

import java.util.Map;

public interface AuthTokenProvider {
    String generateToken(String subject, Map<String, Object> claims, long ttlMillis);
    Map<String, Object> validateAndGetClaims(String token);
}
