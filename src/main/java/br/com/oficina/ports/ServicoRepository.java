package br.com.oficina.ports;

import br.com.oficina.domain.Servico;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository {
    Servico salvar(Servico servico);
    Optional<Servico> buscarPorId(Long id);
    List<Servico> listarTodos();
    List<Servico> buscarPorPalavras(String termos);
}
