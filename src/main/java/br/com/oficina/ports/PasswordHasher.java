package br.com.oficina.ports;

public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashed);
}
