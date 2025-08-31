package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.CategoriaServico;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicos")
public class ServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column
    private String descricao;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal preco;

    @Column(name = "tempo_estimado", nullable = false)
    private Duration tempoEstimado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaServico categoria;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @PrePersist
    public void onCreate() { this.dataCadastro = LocalDateTime.now(); }

    @PreUpdate
    public void onUpdate() { this.dataAtualizacao = LocalDateTime.now(); }

    // getters/setters
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; } public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; } public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; } public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Duration getTempoEstimado() { return tempoEstimado; } public void setTempoEstimado(Duration tempoEstimado) { this.tempoEstimado = tempoEstimado; }
    public br.com.oficina.domain.CategoriaServico getCategoria() { return categoria; } public void setCategoria(br.com.oficina.domain.CategoriaServico categoria) { this.categoria = categoria; }
    public boolean isAtivo() { return ativo; } public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getDataCadastro() { return dataCadastro; } public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; } public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
