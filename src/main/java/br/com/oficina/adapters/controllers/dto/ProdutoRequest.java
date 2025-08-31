package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.CategoriaProduto;
import java.math.BigDecimal;

public class ProdutoRequest {
    public String nomeProduto;
    public String descricaoProduto;
    public CategoriaProduto categoria;
    public BigDecimal precoFinalVenda;
}
