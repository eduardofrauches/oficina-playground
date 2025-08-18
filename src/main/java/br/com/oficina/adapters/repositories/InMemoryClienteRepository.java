package br.com.oficina.adapters.repositories;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;

import java.util.*;

/**
 * Implementação em memória da porta ClienteRepository.
 * Útil para testes locais sem banco/frameworks.
 */
public class InMemoryClienteRepository implements ClienteRepository {

    private final Map<String, Cliente> database = new HashMap<>();

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("cliente não pode ser nulo");
        database.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> findById(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Cliente update(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("cliente não pode ser nulo");
        String id = cliente.getId();
        if (id == null || id.isBlank() || !database.containsKey(id)) {
            throw new NoSuchElementException("Cliente não encontrado para update: " + id);
        }
        database.put(id, cliente);
        return cliente;
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null || id.isBlank()) return false;
        return database.remove(id) != null;
    }
}
