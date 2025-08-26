package br.com.oficina.domain;

import java.util.Objects;

public final class Usuario {
    private Long id;
    private String username;
    private String nome;
    private String email;
    private String password; // NUNCA expor fora do domínio/use case
    private boolean ativo;
    private Long roleId; // referência para tabela "roles"

    public Usuario(Long id, String username, String nome, String email, String password, Boolean ativo, Long roleId) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("username é obrigatório");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome é obrigatório");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email é obrigatório");
        if (roleId == null) throw new IllegalArgumentException("roleId é obrigatório");
        this.id = id;
        this.username = username.trim();
        this.nome = nome.trim();
        this.email = email.trim().toLowerCase();
        this.password = Objects.requireNonNull(password, "password é obrigatório");
        this.ativo = (ativo == null) ? true : ativo;
        this.roleId = roleId;
    }

    public static Usuario novo(String username, String nome, String email, String password, Long roleId) {
        return new Usuario(null, username, nome, email, password, true, roleId);
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isAtivo() { return ativo; }
    public Long getRoleId() { return roleId; }

    public void definirId(Long id) { this.id = id; }
    public void atualizarNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome inválido");
        this.nome = nome.trim();
    }
    public void atualizarEmail(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("email inválido");
        this.email = email.trim().toLowerCase();
    }
    public void atualizarUsername(String username) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("username inválido");
        this.username = username.trim();
    }
    public void atualizarPassword(String password) {
        if (password == null) throw new IllegalArgumentException("password inválido");
        this.password = password;
    }
    public void atualizarRole(Long roleId) {
        if (roleId == null) throw new IllegalArgumentException("roleId inválido");
        this.roleId = roleId;
    }
    public void desativar() { this.ativo = false; }
    public void reativar() { this.ativo = true; }
}
