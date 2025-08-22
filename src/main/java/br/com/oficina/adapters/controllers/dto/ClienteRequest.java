package br.com.oficina.adapters.controllers.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ClienteRequest", description = "Payload para criação/atualização completa de Cliente (PUT/POST).")
public class ClienteRequest {
    @Schema(description = "Nome completo do cliente", example = "Fulano da Silva")
    @NotBlank(message = "nome é obrigatório")
    public String nome;

    @Schema(description = "CPF (apenas dígitos)", example = "12345678900")
    public String cpf;

    @Schema(description = "CNPJ (apenas dígitos)", example = "12345678000199")
    public String cnpj;

    @Schema(description = "E-mail de contato", example = "fulano@ex.com")
    @NotBlank(message = "email é obrigatório")
    @Email(message = "email inválido")
    public String email;

    @Schema(description = "Telefone de contato (apenas dígitos)", example = "11999999999")
    @NotBlank(message = "telefone é obrigatório")
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

    @Schema(description = "UF (2 letras)", example = "SP", minLength = 2, maxLength = 2)
    @Size(min = 2, max = 2, message = "estado (UF) deve ter 2 letras")
    public String estado;

    @Schema(description = "CEP", example = "01000-000")
    public String cep;

    @Schema(description = "Data de nascimento (YYYY-MM-DD)", example = "1990-05-10")
    public LocalDate dataNascimento;

    @Schema(description = "Observações gerais", example = "Cliente VIP")
    public String observacao;

    @Schema(description = "Se o cliente está ativo (default: true)", example = "true")
    public Boolean ativo; // opcional: se null, mantém o atual no update
}
