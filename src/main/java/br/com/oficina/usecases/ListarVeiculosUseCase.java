package br.com.oficina.usecases;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

import java.util.List;

public class ListarVeiculosUseCase {
    private final VeiculoRepository repository;

    public ListarVeiculosUseCase(VeiculoRepository repository) {
        this.repository = repository;
    }

    public List<Veiculo> executar() {
        return repository.findAll();
    }
}
