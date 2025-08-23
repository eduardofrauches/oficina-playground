package br.com.oficina.usecases.cliente;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarClientesUseCase {

    private final ClienteRepository repository;

    public ListarClientesUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> execute() {
        return repository.findAll();
    }
}
