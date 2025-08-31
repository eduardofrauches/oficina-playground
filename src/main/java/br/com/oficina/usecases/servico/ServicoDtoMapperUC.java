package br.com.oficina.usecases.servico;

import br.com.oficina.adapters.controllers.dto.ServicoRequest;
import br.com.oficina.adapters.controllers.dto.ServicoResponse;
import br.com.oficina.domain.Servico;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class ServicoDtoMapperUC {
    public Servico toDomain(ServicoRequest req) {
        return Servico.novo(
                req.nome,
                req.descricao,
                req.preco,
                req.tempoEstimado != null ? Duration.parse(req.tempoEstimado) : Duration.ZERO,
                req.categoria
        );
    }
    public Servico applyUpdates(Servico atual, ServicoRequest req) {
        return atual.atualizar(
                req.nome,
                req.descricao,
                req.preco,
                req.tempoEstimado != null ? Duration.parse(req.tempoEstimado) : atual.getTempoEstimado(),
                req.categoria != null ? req.categoria : atual.getCategoria()
        );
    }
    public ServicoResponse toResponse(Servico s) {
        String dur = s.getTempoEstimado() != null ? s.getTempoEstimado().toString() : null;
        return new ServicoResponse(
                s.getId(), s.getNome(), s.getDescricao(), s.getPreco(),
                dur, s.getCategoria(), s.isAtivo(),
                s.getDataCadastro(), s.getDataAtualizacao()
        );
    }
}
