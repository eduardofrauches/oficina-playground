package br.com.oficina.usecases.cliente;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BuscarClienteUseCase {

    private final ClienteRepository repository;

    public BuscarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Optional<Cliente> execute(Long id) {
        return repository.findById(id);
    }
}
