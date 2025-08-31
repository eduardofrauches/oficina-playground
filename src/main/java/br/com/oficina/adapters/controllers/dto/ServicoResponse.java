package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.CategoriaServico;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServicoResponse {
    public Long id;
    public String nome;
    public String descricao;
    public BigDecimal preco;
    public String tempoEstimado; // ISO-8601
    public CategoriaServico categoria;
    public boolean ativo;
    public LocalDateTime dataCadastro;
    public LocalDateTime dataAtualizacao;

    public ServicoResponse(Long id, String nome, String descricao, BigDecimal preco,
                           String tempoEstimado, CategoriaServico categoria, boolean ativo,
                           LocalDateTime dataCadastro, LocalDateTime dataAtualizacao) {
        this.id = id; this.nome = nome; this.descricao = descricao;
        this.preco = preco; this.tempoEstimado = tempoEstimado; this.categoria = categoria;
        this.ativo = ativo; this.dataCadastro = dataCadastro; this.dataAtualizacao = dataAtualizacao;
    }
}
