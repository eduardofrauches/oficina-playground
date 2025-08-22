package br.com.oficina.adapters.controllers.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClienteResponse", description = "Representação pública do Cliente.")
public class ClienteResponse {
    @Schema(description = "Identificador", example = "1")
    public Long id;

    @Schema(description = "Nome completo", example = "Fulano da Silva")
    public String nome;

    @Schema(description = "CPF", example = "12345678900")
    public String cpf;

    @Schema(description = "CNPJ", example = "12345678000199")
    public String cnpj;

    @Schema(description = "E-mail", example = "fulano@ex.com")
    public String email;

    @Schema(description = "Telefone", example = "11999999999")
    public String telefone;

    // Endereço (achatado)
    @Schema(description = "Logradouro", example = "Rua A")
    public String logradouro;

    @Schema(description = "Número", example = "123")
    public String numero;

    @Schema(description = "Complemento", example = "Apto 12")
    public String complemento;

    @Schema(description = "Bairro", example = "Centro")
    public String bairro;

    @Schema(description = "Cidade", example = "São Paulo")
    public String cidade;

    @Schema(description = "UF (2 letras)", example = "SP")
    public String estado;

    @Schema(description = "CEP", example = "01000-000")
    public String cep;

    @Schema(description = "Data/hora de cadastro (UTC)", example = "2025-08-19T12:34:56")
    public LocalDateTime dataCadastro;

    @Schema(description = "Data de nascimento", example = "1990-05-10")
    public LocalDate dataNascimento;

    @Schema(description = "Observações gerais", example = "Cliente VIP")
    public String observacao;

    @Schema(description = "Flag de ativo", example = "true")
    public Boolean ativo;
}
