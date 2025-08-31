package br.com.oficina.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Servico {
    private final Long id;
    private final String nome;
    private final String descricao;
    private final BigDecimal preco;
    private final Duration tempoEstimado;
    private final CategoriaServico categoria;
    private final boolean ativo;
    private final LocalDateTime dataCadastro;
    private final LocalDateTime dataAtualizacao;

    private Servico(Long id, String nome, String descricao, BigDecimal preco, Duration tempoEstimado,
                    CategoriaServico categoria, boolean ativo, LocalDateTime dataCadastro, LocalDateTime dataAtualizacao) {
        validar(nome, preco, tempoEstimado, categoria);
        this.id = id; this.nome = nome; this.descricao = descricao; this.preco = preco;
        this.tempoEstimado = tempoEstimado; this.categoria = categoria; this.ativo = ativo;
        this.dataCadastro = dataCadastro; this.dataAtualizacao = dataAtualizacao;
    }
    public static Servico novo(String nome, String descricao, BigDecimal preco, Duration tempoEstimado, CategoriaServico categoria) {
        return new Servico(null, nome, descricao, preco, tempoEstimado, categoria, true, null, null);
    }
    public static Servico reconstituir(Long id, String nome, String descricao, BigDecimal preco, Duration tempoEstimado,
                                       CategoriaServico categoria, boolean ativo, LocalDateTime dataCadastro, LocalDateTime dataAtualizacao) {
        return new Servico(id, nome, descricao, preco, tempoEstimado, categoria, ativo, dataCadastro, dataAtualizacao);
    }
    public Servico atualizar(String nome, String descricao, BigDecimal preco, Duration tempoEstimado, CategoriaServico categoria) {
        return new Servico(this.id, nome, descricao, preco, tempoEstimado, categoria, this.ativo, this.dataCadastro, null);
    }
    public Servico desativar() {
        return new Servico(this.id, this.nome, this.descricao, this.preco, this.tempoEstimado, this.categoria, false, this.dataCadastro, null);
    }
    private static void validar(String nome, BigDecimal preco, Duration tempoEstimado, CategoriaServico categoria) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome é obrigatório");
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("preco deve ser >= 0");
        if (tempoEstimado == null || tempoEstimado.isNegative()) throw new IllegalArgumentException("tempoEstimado deve ser >= PT0S");
        if (categoria == null) throw new IllegalArgumentException("categoria é obrigatória");
    }
    public Long getId() { return id; } public String getNome() { return nome; } public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; } public java.time.Duration getTempoEstimado() { return tempoEstimado; }
    public CategoriaServico getCategoria() { return categoria; } public boolean isAtivo() { return ativo; }
    public LocalDateTime getDataCadastro() { return dataCadastro; } public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    @Override public boolean equals(Object o) { if (this == o) return true; if (o == null || getClass() != o.getClass()) return false;
        Servico s = (Servico) o; return java.util.Objects.equals(id, s.id); }
    @Override public int hashCode() { return java.util.Objects.hash(id); }
}
