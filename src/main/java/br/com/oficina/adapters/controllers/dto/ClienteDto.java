package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClienteDto {
    public Long id;
    public String nome;
    public String cpf;
    public String cnpj;
    public String email;
    public String telefone;
    public String logradouro;
    public String numero;
    public String complemento;
    public String bairro;
    public String cidade;
    public String estado;
    public String cep;
    public LocalDateTime dataCadastro;
    public LocalDate dataNascimento;
    public String observacao;
    public Boolean ativo;

    public static Cliente toDomain(ClienteDto d) {
        Endereco end = (d.logradouro != null || d.cep != null)
                ? new Endereco(d.logradouro, d.numero, d.complemento, d.bairro, d.cidade, d.estado, d.cep)
                : null;
        return new Cliente(
                d.id,
                d.nome,
                d.cpf,
                d.cnpj,
                d.email,
                d.telefone,
                end,
                d.dataCadastro,
                d.dataNascimento,
                d.observacao,
                d.ativo
        );
    }

    public static ClienteDto fromDomain(Cliente c) {
        ClienteDto d = new ClienteDto();
        d.id = c.getId();
        d.nome = c.getNome();
        d.cpf = c.getCpf();
        d.cnpj = c.getCnpj();
        d.email = c.getEmail();
        d.telefone = c.getTelefone();
        if (c.getEndereco() != null) {
            d.logradouro = c.getEndereco().getLogradouro();
            d.numero = c.getEndereco().getNumero();
            d.complemento = c.getEndereco().getComplemento();
            d.bairro = c.getEndereco().getBairro();
            d.cidade = c.getEndereco().getCidade();
            d.estado = c.getEndereco().getEstado();
            d.cep = c.getEndereco().getCep();
        }
        d.dataCadastro = c.getDataCadastro();
        d.dataNascimento = c.getDataNascimento();
        d.observacao = c.getObservacao();
        d.ativo = c.isAtivo();
        return d;
    }
}
