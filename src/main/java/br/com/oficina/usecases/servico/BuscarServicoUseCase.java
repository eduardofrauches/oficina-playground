package br.com.oficina.usecases.servico;

import br.com.oficina.adapters.controllers.dto.ServicoResponse;
import br.com.oficina.domain.Servico;
import br.com.oficina.ports.ServicoRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarServicoUseCase {
    private final ServicoRepository repo;
    private final ServicoDtoMapperUC mapper;

    public BuscarServicoUseCase(ServicoRepository repo, ServicoDtoMapperUC mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public ServicoResponse executar(Long id) {
        Servico s = repo.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado"));
        return mapper.toResponse(s);
    }
}
