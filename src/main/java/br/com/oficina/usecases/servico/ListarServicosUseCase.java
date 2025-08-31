package br.com.oficina.usecases.servico;

import br.com.oficina.adapters.controllers.dto.ServicoResponse;
import br.com.oficina.ports.ServicoRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ListarServicosUseCase {
    private final ServicoRepository repo;
    private final ServicoDtoMapperUC mapper;

    public ListarServicosUseCase(ServicoRepository repo, ServicoDtoMapperUC mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ServicoResponse> executar() {
        return repo.listarTodos().stream().map(mapper::toResponse).toList();
    }
}
