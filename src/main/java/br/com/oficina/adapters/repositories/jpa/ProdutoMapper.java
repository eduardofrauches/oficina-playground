package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Produto;

public final class ProdutoMapper {
    private ProdutoMapper(){}
    public static Produto toDomain(ProdutoEntity e){
        if (e == null) return null;
        return new Produto(e.getId(), e.getNomeProduto(), e.getDescricaoProduto(), e.getCategoria(),
                e.getAtivo() != null ? e.getAtivo() : Boolean.TRUE, e.getPrecoFinalVenda());
    }
    public static ProdutoEntity toEntity(Produto d){
        ProdutoEntity e = new ProdutoEntity();
        e.setId(d.getId());
        e.setNomeProduto(d.getNomeProduto());
        e.setDescricaoProduto(d.getDescricaoProduto());
        e.setCategoria(d.getCategoria());
        e.setAtivo(d.isAtivo());
        e.setPrecoFinalVenda(d.getPrecoFinalVenda());
        return e;
    }
}
