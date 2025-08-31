package br.com.oficina.usecases.servico;

import br.com.oficina.adapters.controllers.dto.ServicoResponse;
import br.com.oficina.ports.ServicoRepository;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BuscarServicosPorPalavrasUseCase {
    private final ServicoRepository repo;
    private final ServicoDtoMapperUC mapper;

    public BuscarServicosPorPalavrasUseCase(ServicoRepository repo, ServicoDtoMapperUC mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<ServicoResponse> executar(String termos) {
        return repo.buscarPorPalavras(termos).stream().map(mapper::toResponse).toList();
    }
}
