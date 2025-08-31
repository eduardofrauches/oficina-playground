package br.com.oficina.usecases.servico;

import br.com.oficina.adapters.controllers.dto.ServicoRequest;
import br.com.oficina.adapters.controllers.dto.ServicoResponse;
import br.com.oficina.domain.Servico;
import br.com.oficina.ports.ServicoRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarServicoUseCase {
    private final ServicoRepository repo;
    private final ServicoDtoMapperUC mapper;

    public AtualizarServicoUseCase(ServicoRepository repo, ServicoDtoMapperUC mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public ServicoResponse executar(Long id, ServicoRequest req) {
        Servico atual = repo.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        Servico atualizado = mapper.applyUpdates(atual, req);
        Servico salvo = repo.salvar(atualizado);
        return mapper.toResponse(salvo);
    }
}
