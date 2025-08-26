package br.com.oficina.adapters.controllers.dto;

public record LoginResponse(String token, long expiresInMillis, String tokenType) {
    public static LoginResponse bearer(String token, long expiresInMillis) {
        return new LoginResponse(token, expiresInMillis, "Bearer");
    }
}
