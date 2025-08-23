package br.com.oficina.usecases.veiculo;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarVeiculosPorClienteUseCase {
    private final VeiculoRepository repo;

    public ListarVeiculosPorClienteUseCase(VeiculoRepository repo) {
        this.repo = repo;
    }

    public List<Veiculo> executar(Long clienteId) {
        if (clienteId == null) return List.of();
        return repo.findByClienteId(clienteId);
    }
}
