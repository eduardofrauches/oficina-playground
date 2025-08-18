package br.com.oficina.domain;

import java.math.BigDecimal;
import java.util.List;

public class Orcamento {
    private final String id;
    private final Cliente cliente;
    private final Veiculo veiculo;
    private final List<Servico> servicos;
    private final List<Peca> pecas;

    public Orcamento(String id, Cliente cliente, Veiculo veiculo, List<Servico> servicos, List<Peca> pecas) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id é obrigatório");
        if (cliente == null) throw new IllegalArgumentException("cliente é obrigatório");
        if (veiculo == null) throw new IllegalArgumentException("veículo é obrigatório");

        this.id = id;
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.servicos = servicos;
        this.pecas = pecas;
    }

    public BigDecimal calcularTotal() {
        BigDecimal totalServicos = servicos.stream()
                .map(Servico::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPecas = pecas.stream()
                .map(Peca::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalServicos.add(totalPecas);
    }

    public String getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Veiculo getVeiculo() { return veiculo; }
    public List<Servico> getServicos() { return servicos; }
    public List<Peca> getPecas() { return pecas; }
}
