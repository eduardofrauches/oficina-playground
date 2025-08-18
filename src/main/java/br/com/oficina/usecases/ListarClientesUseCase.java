package br.com.oficina.usecases;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;

import java.util.List;

public class ListarClientesUseCase {
    private final ClienteRepository repository;

    public ListarClientesUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> executar() {
        return repository.findAll();
    }
}
