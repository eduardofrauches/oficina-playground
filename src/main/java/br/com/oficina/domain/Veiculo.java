package br.com.oficina.domain;

import java.util.Objects;

/**
 * Entidade de domínio pura (sem anotações de framework).
 * Mantém os mesmos atributos do projeto original + clienteId como chave do cliente.
 */
public class Veiculo {

    private Long id;
    private final String placa;
    private final String marca;
    private final String modelo;
    private final Integer ano;
    private final String cor;
    private final String observacoes;
    private final Long clienteId;
    private final boolean ativo;

    public Veiculo(
            Long id,
            String placa,
            String marca,
            String modelo,
            Integer ano,
            String cor,
            String observacoes,
            Long clienteId,
            boolean ativo
    ) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("placa é obrigatória");
        }
        if (clienteId == null) {
            throw new IllegalArgumentException("clienteId é obrigatório");
        }
        if (ano != null && ano < 1900) {
            throw new IllegalArgumentException("ano inválido (mínimo 1900)");
        }
        this.id = id;
        this.placa = placa.trim().toUpperCase();
        this.marca = nullSafe(marca);
        this.modelo = nullSafe(modelo);
        this.ano = ano;
        this.cor = nullSafe(cor);
        this.observacoes = nullSafe(observacoes);
        this.clienteId = clienteId;
        this.ativo = ativo;
    }

    private String nullSafe(String v) {
        return v == null ? null : v.trim();
    }

    public Long getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public String getCor() {
        return cor;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    /** Cria uma cópia com id substituído (útil para update). */
    public Veiculo withId(Long newId) {
        return new Veiculo(newId, placa, marca, modelo, ano, cor, observacoes, clienteId, ativo);
    }

    public void definirId(Long id) {
    if (this.id != null && !this.id.equals(id)) {
        throw new IllegalStateException("ID do veículo já definido e divergente");
    }
    this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Veiculo v)) return false;
        return Objects.equals(id, v.id) && Objects.equals(placa, v.placa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, placa);
    }
}
