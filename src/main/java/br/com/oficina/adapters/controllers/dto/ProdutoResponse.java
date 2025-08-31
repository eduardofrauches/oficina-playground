package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.CategoriaProduto;
import java.math.BigDecimal;

public record ProdutoResponse(
        Long id,
        String nomeProduto,
        String descricaoProduto,
        CategoriaProduto categoria,
        boolean ativo,
        BigDecimal precoFinalVenda
) {}
