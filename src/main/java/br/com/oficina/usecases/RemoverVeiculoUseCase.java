package br.com.oficina.usecases;

import br.com.oficina.ports.VeiculoRepository;

public class RemoverVeiculoUseCase {
    private final VeiculoRepository repository;

    public RemoverVeiculoUseCase(VeiculoRepository repository) {
        this.repository = repository;
    }

    public boolean executar(String id) {
        return repository.deleteById(id);
    }
}
