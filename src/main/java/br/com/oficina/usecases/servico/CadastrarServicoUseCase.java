package br.com.oficina.usecases.servico;

import br.com.oficina.adapters.controllers.dto.ServicoRequest;
import br.com.oficina.adapters.controllers.dto.ServicoResponse;
import br.com.oficina.domain.Servico;
import br.com.oficina.ports.ServicoRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastrarServicoUseCase {
    private final ServicoRepository repo;
    private final ServicoDtoMapperUC mapper;

    public CadastrarServicoUseCase(ServicoRepository repo, ServicoDtoMapperUC mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public ServicoResponse executar(ServicoRequest req) {
        Servico novo = mapper.toDomain(req);
        Servico salvo = repo.salvar(novo);
        return mapper.toResponse(salvo);
    }
}
