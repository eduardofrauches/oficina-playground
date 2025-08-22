package br.com.oficina.adapters.controllers.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para PATCH (campos opcionais).
 * Só valida formatos quando presentes (ex.: email, UF).
 */
@Schema(name = "ClientePatchRequest", description = "Payload para alteração parcial (PATCH). Envie apenas os campos que deseja mudar.")
public class ClientePatchRequest {
    @Schema(description = "Nome completo do cliente", example = "Fulano da Silva")
    public String nome;

    @Schema(description = "CPF (apenas dígitos)", example = "12345678900")
    public String cpf;

    @Schema(description = "CNPJ (apenas dígitos)", example = "12345678000199")
    public String cnpj;

    @Schema(description = "E-mail de contato", example = "fulano.silva@ex.com")
    @Email(message = "email inválido")
    public String email;

    @Schema(description = "Telefone de contato (apenas dígitos)", example = "11988887777")
    public String telefone;

    // Endereço (achatado)
    @Schema(description = "Logradouro", example = "Rua B")
    public String logradouro;

    @Schema(description = "Número", example = "200")
    public String numero;

    @Schema(description = "Complemento", example = "Casa")
    public String complemento;

    @Schema(description = "Bairro", example = "Jardins")
    public String bairro;

    @Schema(description = "Cidade", example = "São Paulo")
    public String cidade;

    @Schema(description = "UF (2 letras)", example = "SP", minLength = 2, maxLength = 2)
    @Size(min = 2, max = 2, message = "estado (UF) deve ter 2 letras")
    public String estado;

    @Schema(description = "CEP", example = "04500-000")
    public String cep;

    @Schema(description = "Data de nascimento (YYYY-MM-DD)", example = "1990-05-10")
    public LocalDate dataNascimento;

    @Schema(description = "Observações gerais", example = "Atualizado via PATCH")
    public String observacao;

    @Schema(description = "Ativar/desativar cliente", example = "true")
    public Boolean ativo; // se null, mantém atual
}
