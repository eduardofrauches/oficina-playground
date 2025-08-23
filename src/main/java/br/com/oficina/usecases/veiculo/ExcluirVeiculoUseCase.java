package br.com.oficina.usecases.veiculo;

import org.springframework.stereotype.Component;

import br.com.oficina.ports.VeiculoRepository;

/**
 * Exclusão de veículo por ID.
 */
@Component
public class ExcluirVeiculoUseCase {
    private final VeiculoRepository repo;

    public ExcluirVeiculoUseCase(VeiculoRepository repo) {
        this.repo = repo;
    }

    public boolean executar(Long id) {
        return repo.deleteById(id);
    }
}
