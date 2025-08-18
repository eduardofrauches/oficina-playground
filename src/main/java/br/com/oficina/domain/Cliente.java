package br.com.oficina.domain;

public class Cliente {
    private final String id;
    private final String nome;
    private final String email;

    public Cliente(String id, String nome, String email) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id é obrigatório");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("nome é obrigatório");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email é obrigatório");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "Cliente{id='%s', nome='%s', email='%s'}".formatted(id, nome, email);
    }
}
