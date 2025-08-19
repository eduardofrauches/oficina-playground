package br.com.oficina.domain;

import br.com.oficina.domain.vo.Endereco;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Cliente {
    private Long id;                       // gerado fora do domínio (db)
    private String nome;                   // obrigatório
    private String cpf;                    // opcional
    private String cnpj;                   // opcional
    private String email;                  // obrigatório
    private String telefone;               // obrigatório
    private Endereco endereco;             // opcional
    private LocalDateTime dataCadastro;    // default agora
    private LocalDate dataNascimento;      // opcional
    private String observacao;             // opcional
    private boolean ativo;                 // default true

    public Cliente(Long id, String nome, String cpf, String cnpj, String email,
                   String telefone, Endereco endereco, LocalDateTime dataCadastro,
                   LocalDate dataNascimento, String observacao, Boolean ativo) {

        setNome(nome);
        setEmail(email);
        setTelefone(telefone);

        this.id = id;
        this.cpf = trimOrNull(cpf);
        this.cnpj = trimOrNull(cnpj);
        this.endereco = endereco;
        this.dataCadastro = (dataCadastro != null) ? dataCadastro : LocalDateTime.now();
        this.dataNascimento = dataNascimento;
        this.observacao = trimOrNull(observacao);
        this.ativo = (ativo == null) ? true : ativo;
    }

    // Fábrica básica para criação sem ID (será atribuído pelo repositório/DB)
    public static Cliente novo(String nome, String email, String telefone,
                               String cpf, String cnpj, Endereco endereco,
                               LocalDate dataNascimento, String observacao) {
        return new Cliente(null, nome, cpf, cnpj, email, telefone, endereco,
                LocalDateTime.now(), dataNascimento, observacao, true);
    }

    private static String trimOrNull(String s) { return s == null ? null : s.trim(); }
    private static void requireNonBlank(String v, String msg) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(msg);
    }

    private void setNome(String nome) {
        requireNonBlank(nome, "nome é obrigatório");
        this.nome = nome.trim();
    }
    private void setEmail(String email) {
        requireNonBlank(email, "email é obrigatório");
        this.email = email.trim();
    }
    private void setTelefone(String telefone) {
        requireNonBlank(telefone, "telefone é obrigatório");
        this.telefone = telefone.trim();
    }

    // Comportamentos de domínio
    public void ativar() { this.ativo = true; }
    public void desativar() { this.ativo = false; }
    public void atualizarEndereco(Endereco novo) { this.endereco = novo; }
    public void atualizarContato(String novoEmail, String novoTelefone) {
        setEmail(novoEmail);
        setTelefone(novoTelefone);
    }

    // Getters (sem setters públicos — manter invariantes)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getCnpj() { return cnpj; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public Endereco getEndereco() { return endereco; }
    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getObservacao() { return observacao; }
    public boolean isAtivo() { return ativo; }

    // ID poderá ser setado pelo repositório (persistência) ao salvar
    public void definirId(Long id) { this.id = id; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente c = (Cliente) o;
        return Objects.equals(id, c.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
