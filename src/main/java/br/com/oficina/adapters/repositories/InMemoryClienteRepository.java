package br.com.oficina.adapters.repositories;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;

import java.util.*;

public class InMemoryClienteRepository implements ClienteRepository {
    private final Map<String, Cliente> database = new HashMap<>();

    @Override
    public Cliente save(Cliente cliente) {
        database.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(database.values());
    }
}
