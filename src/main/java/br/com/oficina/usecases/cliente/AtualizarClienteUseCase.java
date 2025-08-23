package br.com.oficina.usecases.cliente;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarClienteUseCase {

    private final ClienteRepository repository;

    public AtualizarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente execute(Cliente cliente) {
        // regra simples: save() atualiza quando já tem ID
        return repository.save(cliente);
    }
}
