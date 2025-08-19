package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;

public final class ClienteMapper {

    private ClienteMapper() {}

    public static Cliente toDomain(ClienteEntity e) {
        if (e == null) return null;
        Endereco end = null;
        if (e.getEndereco() != null) {
            end = new Endereco(
                    e.getEndereco().getLogradouro(),
                    e.getEndereco().getNumero(),
                    e.getEndereco().getComplemento(),
                    e.getEndereco().getBairro(),
                    e.getEndereco().getCidade(),
                    e.getEndereco().getEstado(),
                    e.getEndereco().getCep()
            );
        }

        Cliente c = new Cliente(
                e.getId(),
                e.getNome(),
                e.getCpf(),
                e.getCnpj(),
                e.getEmail(),
                e.getTelefone(),
                end,
                e.getDataCadastro(),
                e.getDataNascimento(),
                e.getObservacao(),
                e.getAtivo()
        );
        // ID já vem preenchido do entity
        return c;
    }

    public static ClienteEntity toEntity(Cliente c) {
        if (c == null) return null;
        ClienteEntity e = new ClienteEntity();
        e.setId(c.getId());
        e.setNome(c.getNome());
        e.setCpf(c.getCpf());
        e.setCnpj(c.getCnpj());
        e.setEmail(c.getEmail());
        e.setTelefone(c.getTelefone());

        if (c.getEndereco() != null) {
            EnderecoEmbeddable emb = new EnderecoEmbeddable();
            emb.setLogradouro(c.getEndereco().getLogradouro());
            emb.setNumero(c.getEndereco().getNumero());
            emb.setComplemento(c.getEndereco().getComplemento());
            emb.setBairro(c.getEndereco().getBairro());
            emb.setCidade(c.getEndereco().getCidade());
            emb.setEstado(c.getEndereco().getEstado());
            emb.setCep(c.getEndereco().getCep());
            e.setEndereco(emb);
        }

        e.setDataCadastro(c.getDataCadastro());
        e.setDataNascimento(c.getDataNascimento());
        e.setObservacao(c.getObservacao());
        e.setAtivo(c.isAtivo());
        return e;
    }
}
