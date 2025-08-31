package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Servico;

public final class ServicoMapper {
    private ServicoMapper(){}
    public static Servico toDomain(ServicoEntity e) {
        return Servico.reconstituir(
                e.getId(), e.getNome(), e.getDescricao(), e.getPreco(),
                e.getTempoEstimado(), e.getCategoria(), e.isAtivo(),
                e.getDataCadastro(), e.getDataAtualizacao()
        );
    }
    public static ServicoEntity toEntity(Servico d) {
        ServicoEntity e = new ServicoEntity();
        e.setId(d.getId()); e.setNome(d.getNome()); e.setDescricao(d.getDescricao());
        e.setPreco(d.getPreco()); e.setTempoEstimado(d.getTempoEstimado());
        e.setCategoria(d.getCategoria()); e.setAtivo(d.isAtivo());
        e.setDataCadastro(d.getDataCadastro()); e.setDataAtualizacao(d.getDataAtualizacao());
        return e;
    }
}
