package br.com.oficina.usecases;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;

import java.util.Optional;

public class BuscarClienteUseCase {
    private final ClienteRepository repository;

    public BuscarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Optional<Cliente> executar(Long id) {
        return repository.findById(id);
    }
}
