package br.com.oficina.usecases.veiculo;

import org.springframework.stereotype.Component;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

import java.util.List;

/**
 * Listagem de veículos.
 */
@Component
public class ListarVeiculosUseCase {
    private final VeiculoRepository repo;

    public ListarVeiculosUseCase(VeiculoRepository repo) {
        this.repo = repo;
    }

    public List<Veiculo> executar() {
        return repo.findAll();
    }
}
