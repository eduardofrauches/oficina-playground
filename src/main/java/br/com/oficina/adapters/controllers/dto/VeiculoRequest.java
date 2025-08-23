package br.com.oficina.adapters.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de entrada para Veículo")
public class VeiculoRequest {
    @Schema(example = "ABC1D23", description = "Placa do veículo (única)")
    public String placa;
    @Schema(example = "Fiat")   public String marca;
    @Schema(example = "Argo")   public String modelo;
    @Schema(example = "2020")   public Integer ano;
    @Schema(example = "Prata")  public String cor;
    @Schema(example = "Obs")    public String observacoes;
    @Schema(example = "1")      public Long clienteId;
    @Schema(example = "true")   public Boolean ativo;
}
