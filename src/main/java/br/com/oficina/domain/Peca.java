package br.com.oficina.domain;

import java.math.BigDecimal;

public class Peca {
    private final String id;
    private final String nome;
    private final BigDecimal preco;

    public Peca(String id, String nome, BigDecimal preco) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id é obrigatório");
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("nome é obrigatório");
        if (preco == null || preco.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("preço inválido");

        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }

    @Override
    public String toString() {
        return "Peca{id='%s', nome='%s', preco=%s}"
                .formatted(id, nome, preco);
    }
}
