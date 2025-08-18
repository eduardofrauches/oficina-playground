package br.com.oficina.usecases;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

import java.util.Optional;

public class BuscarVeiculoUseCase {
    private final VeiculoRepository repository;

    public BuscarVeiculoUseCase(VeiculoRepository repository) {
        this.repository = repository;
    }

    public Optional<Veiculo> executar(String id) {
        return repository.findById(id);
    }
}
