package br.com.oficina.domain;

import java.math.BigDecimal;

public class Servico {
    private final String id;
    private final String descricao;
    private final BigDecimal preco;

    public Servico(String id, String descricao, BigDecimal preco) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id é obrigatório");
        if (descricao == null || descricao.isBlank()) throw new IllegalArgumentException("descrição é obrigatória");
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("preço inválido");

        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getId() { return id; }
    public String getDescricao() { return descricao; }
    public BigDecimal getPreco() { return preco; }

    @Override
    public String toString() {
        return "Servico{id='%s', descricao='%s', preco=%s}"
                .formatted(id, descricao, preco);
    }
}
