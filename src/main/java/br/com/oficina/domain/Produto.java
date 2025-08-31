package br.com.oficina.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidade de domínio pura (sem anotações de framework).
 */
public class Produto {

    private Long id;
    private final String nomeProduto;
    private final String descricaoProduto;
    private final CategoriaProduto categoria;
    private final boolean ativo;
    private final BigDecimal precoFinalVenda;

    public Produto(Long id, String nomeProduto, String descricaoProduto,
                   CategoriaProduto categoria, boolean ativo, BigDecimal precoFinalVenda) {
        if (nomeProduto == null || nomeProduto.isBlank()) throw new IllegalArgumentException("nomeProduto é obrigatório");
        if (categoria == null) throw new IllegalArgumentException("categoria é obrigatória");
        if (precoFinalVenda == null || precoFinalVenda.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("precoFinalVenda deve ser >= 0");
        this.id = id;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.categoria = categoria;
        this.ativo = ativo;
        this.precoFinalVenda = precoFinalVenda;
    }

    public static Produto novo(String nomeProduto, String descricaoProduto,
                               CategoriaProduto categoria, BigDecimal precoFinalVenda) {
        return new Produto(null, nomeProduto, descricaoProduto, categoria, true, precoFinalVenda);
    }

    public Produto atualizar(String nomeProduto, String descricaoProduto,
                             CategoriaProduto categoria, BigDecimal precoFinalVenda) {
        return new Produto(this.id, nomeProduto, descricaoProduto, categoria, this.ativo, precoFinalVenda);
    }

    public Produto desativar() {
        return new Produto(this.id, this.nomeProduto, this.descricaoProduto, this.categoria, false, this.precoFinalVenda);
    }

    public Long getId() { return id; }
    public String getNomeProduto() { return nomeProduto; }
    public String getDescricaoProduto() { return descricaoProduto; }
    public CategoriaProduto getCategoria() { return categoria; }
    public boolean isAtivo() { return ativo; }
    public BigDecimal getPrecoFinalVenda() { return precoFinalVenda; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
