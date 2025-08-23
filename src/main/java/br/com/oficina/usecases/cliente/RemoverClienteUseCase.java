package br.com.oficina.usecases.cliente;

import br.com.oficina.ports.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class RemoverClienteUseCase {

    private final ClienteRepository repository;

    public RemoverClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public boolean execute(Long id) {
        return repository.deleteById(id);
    }
}
