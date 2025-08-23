package br.com.oficina.usecases.veiculo;

import org.springframework.stereotype.Component;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

import java.util.Optional;

/**
 * Busca de veículo por ID.
 */
@Component
public class BuscarVeiculoUseCase {
    private final VeiculoRepository repo;

    public BuscarVeiculoUseCase(VeiculoRepository repo) {
        this.repo = repo;
    }

    public Optional<Veiculo> executar(Long id) {
        return repo.findById(id);
    }
}
