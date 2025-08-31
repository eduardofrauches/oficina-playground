package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.CategoriaServico;
import java.math.BigDecimal;

public class ServicoRequest {
    public String nome;
    public String descricao;
    public BigDecimal preco;
    /** ISO-8601 Duration: ex PT1M30S */
    public String tempoEstimado;
    public CategoriaServico categoria;
}
