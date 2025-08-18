package br.com.oficina.usecases;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

public class AtualizarVeiculoUseCase {
    private final VeiculoRepository repository;

    public AtualizarVeiculoUseCase(VeiculoRepository repository) {
        this.repository = repository;
    }

    public Veiculo executar(Veiculo veiculo) {
        return repository.update(veiculo);
    }
}
