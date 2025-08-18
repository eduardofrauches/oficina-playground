package br.com.oficina.usecases;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;

public class CadastrarClienteUseCase {
    private final ClienteRepository repository;

    public CadastrarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente executar(Cliente cliente) {
        return repository.save(cliente);
    }
}
