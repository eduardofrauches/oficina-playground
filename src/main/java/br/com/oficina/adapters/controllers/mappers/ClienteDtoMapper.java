
package br.com.oficina.adapters.controllers.mappers;

import br.com.oficina.adapters.controllers.dto.ClienteRequest;
import br.com.oficina.adapters.controllers.dto.ClienteResponse;
import br.com.oficina.adapters.controllers.dto.ClientePatchRequest;
import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;

import java.time.LocalDateTime;

public final class ClienteDtoMapper {

    private ClienteDtoMapper() {}

    private static Endereco toEndereco(ClienteRequest d) {
        if (d == null) return null;
        boolean hasAddr =
                d.logradouro != null || d.numero != null || d.complemento != null ||
                d.bairro != null || d.cidade != null || d.estado != null || d.cep != null;
        if (!hasAddr) return null;
        return new Endereco(d.logradouro, d.numero, d.complemento, d.bairro, d.cidade, d.estado, d.cep);
    }

    public static Cliente toDomainForCreate(ClienteRequest d) {
        Endereco end = toEndereco(d);
        Cliente c = Cliente.novo(
                d.nome, d.email, d.telefone, d.cpf, d.cnpj, end, d.dataNascimento, d.observacao
        );
        if (d.ativo != null) {
            if (Boolean.TRUE.equals(d.ativo)) c.ativar(); else c.desativar();
        }
        return c;
    }

    public static Cliente toDomainForUpdate(Long id, ClienteRequest d, LocalDateTime dataCadastroAtual, boolean ativoAtual) {
        Endereco end = toEndereco(d);
        Boolean ativo = (d.ativo != null) ? d.ativo : ativoAtual;
        // Usa o construtor completo para preservar dataCadastro
        return new Cliente(
                id, d.nome, d.cpf, d.cnpj, d.email, d.telefone, end,
                dataCadastroAtual, d.dataNascimento, d.observacao, ativo
        );
    }

    public static ClienteResponse toResponse(Cliente c) {
        ClienteResponse r = new ClienteResponse();
        r.id = c.getId();
        r.nome = c.getNome();
        r.cpf = c.getCpf();
        r.cnpj = c.getCnpj();
        r.email = c.getEmail();
        r.telefone = c.getTelefone();
        if (c.getEndereco() != null) {
            r.logradouro = c.getEndereco().getLogradouro();
            r.numero = c.getEndereco().getNumero();
            r.complemento = c.getEndereco().getComplemento();
            r.bairro = c.getEndereco().getBairro();
            r.cidade = c.getEndereco().getCidade();
            r.estado = c.getEndereco().getEstado();
            r.cep = c.getEndereco().getCep();
        }
        r.dataCadastro = c.getDataCadastro();
        r.dataNascimento = c.getDataNascimento();
        r.observacao = c.getObservacao();
        r.ativo = c.isAtivo();
        return r;
    }

    // ===== PATCH support =====

    private static String norm(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private static Endereco mergeEndereco(Endereco atual, ClientePatchRequest p) {
        boolean hasPatchAddr =
                p.logradouro != null || p.numero != null || p.complemento != null ||
                p.bairro != null || p.cidade != null || p.estado != null || p.cep != null;
        if (!hasPatchAddr) {
            return atual; // nada mudou
        }
        String logradouro = (p.logradouro != null) ? norm(p.logradouro) : (atual != null ? atual.getLogradouro() : null);
        String numero     = (p.numero     != null) ? norm(p.numero)     : (atual != null ? atual.getNumero()     : null);
        String complemento= (p.complemento!= null) ? norm(p.complemento): (atual != null ? atual.getComplemento(): null);
        String bairro     = (p.bairro     != null) ? norm(p.bairro)     : (atual != null ? atual.getBairro()     : null);
        String cidade     = (p.cidade     != null) ? norm(p.cidade)     : (atual != null ? atual.getCidade()     : null);
        String estado     = (p.estado     != null) ? norm(p.estado)     : (atual != null ? atual.getEstado()     : null);
        String cep        = (p.cep        != null) ? norm(p.cep)        : (atual != null ? atual.getCep()        : null);

        boolean allNull = logradouro == null && numero == null && complemento == null &&
                          bairro == null && cidade == null && estado == null && cep == null;
        if (allNull) return null; // patch limpou tudo

        return new Endereco(logradouro, numero, complemento, bairro, cidade, estado, cep);
    }

    public static Cliente mergeForPatch(Cliente atual, ClientePatchRequest p) {
        if (atual == null) return null;
        String nome      = (p.nome      != null) ? norm(p.nome)      : atual.getNome();
        String cpf       = (p.cpf       != null) ? norm(p.cpf)       : atual.getCpf();
        String cnpj      = (p.cnpj      != null) ? norm(p.cnpj)      : atual.getCnpj();
        String email     = (p.email     != null) ? norm(p.email)     : atual.getEmail();
        String telefone  = (p.telefone  != null) ? norm(p.telefone)  : atual.getTelefone();
        var endereco     = mergeEndereco(atual.getEndereco(), p);
        var dataCadastro = atual.getDataCadastro();
        var dataNasc     = (p.dataNascimento != null) ? p.dataNascimento : atual.getDataNascimento();
        String observ    = (p.observacao != null) ? norm(p.observacao) : atual.getObservacao();
        boolean ativo    = (p.ativo != null) ? p.ativo : atual.isAtivo();

        return new Cliente(
                atual.getId(),
                nome, cpf, cnpj, email, telefone, endereco,
                dataCadastro, dataNasc, observ, ativo
        );
    }
}
