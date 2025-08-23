package br.com.oficina.usecases.cliente;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastrarClienteUseCase {

    private final ClienteRepository repository;

    public CadastrarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente execute(Cliente cliente) {
        return repository.save(cliente);
    }
}
