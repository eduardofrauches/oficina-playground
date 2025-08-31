package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.Produto;

public final class ProdutoDtoMapper {
    private ProdutoDtoMapper(){}
    public static ProdutoResponse toResponse(Produto p){
        return new ProdutoResponse(p.getId(), p.getNomeProduto(), p.getDescricaoProduto(),
                p.getCategoria(), p.isAtivo(), p.getPrecoFinalVenda());
    }
}
