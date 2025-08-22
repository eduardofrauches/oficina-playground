package br.com.oficina.usecases;

import br.com.oficina.ports.ClienteRepository;

public class RemoverClienteUseCase {
    private final ClienteRepository repository;

    public RemoverClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public boolean executar(Long id) {
        return repository.deleteById(id);
    }
}
