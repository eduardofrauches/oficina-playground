package br.com.oficina.usecases;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

public class CadastrarVeiculoUseCase {
    private final VeiculoRepository repository;

    public CadastrarVeiculoUseCase(VeiculoRepository repository) {
        this.repository = repository;
    }

    public Veiculo executar(Veiculo veiculo) {
        return repository.save(veiculo);
    }
}
