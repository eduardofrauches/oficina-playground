package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.CategoriaProduto;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos", indexes = {
        @Index(name="idx_produtos_nome", columnList = "nome_produto")
})
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome_produto", nullable=false)
    private String nomeProduto;

    @Column(name="descricao_produto")
    private String descricaoProduto;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private CategoriaProduto categoria;

    @Column(nullable=false)
    private Boolean ativo = true;

    @Column(name="preco_final_venda", nullable=false, precision=15, scale=2)
    private BigDecimal precoFinalVenda;

    public Long getId() { return id; }
    public String getNomeProduto() { return nomeProduto; }
    public String getDescricaoProduto() { return descricaoProduto; }
    public CategoriaProduto getCategoria() { return categoria; }
    public Boolean getAtivo() { return ativo; }
    public BigDecimal getPrecoFinalVenda() { return precoFinalVenda; }

    public void setId(Long id) { this.id = id; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public void setDescricaoProduto(String descricaoProduto) { this.descricaoProduto = descricaoProduto; }
    public void setCategoria(CategoriaProduto categoria) { this.categoria = categoria; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public void setPrecoFinalVenda(BigDecimal precoFinalVenda) { this.precoFinalVenda = precoFinalVenda; }
}
