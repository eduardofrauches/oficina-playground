package br.com.oficina.adapters.repositories;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("dev")
public class InMemoryClienteRepository implements ClienteRepository {

    private final Map<Long, Cliente> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == null) {
            Long id = seq.incrementAndGet();
            // domínio permite receber o id gerado pelo repositório
            cliente.definirId(id);
        }
        db.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean deleteById(Long id) {
        return db.remove(id) != null;
    }
}
