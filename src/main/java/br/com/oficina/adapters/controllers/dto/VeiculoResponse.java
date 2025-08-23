package br.com.oficina.adapters.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta de Veículo")
public class VeiculoResponse {
    public Long id;
    public String placa;
    public String marca;
    public String modelo;
    public Integer ano;
    public String cor;
    public String observacoes;
    public Long clienteId;
    public Boolean ativo;
}
