package br.com.oficina.adapters.repositories;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("dev")
public class InMemoryVeiculoRepository implements VeiculoRepository {

    private final Map<Long, Veiculo> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Veiculo save(Veiculo v) {
        Long id = v.getId();
        if (id == null) {
            id = seq.incrementAndGet();
        }
        Veiculo saved = v.withId(id);
        store.put(id, saved);
        return saved;
    }

    @Override
    public Optional<Veiculo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Veiculo> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }

    @Override
    public Optional<Veiculo> findByPlaca(String placa) {
        if (placa == null) return Optional.empty();
        String key = placa.trim().toUpperCase();
        return store.values().stream()
                .filter(v -> key.equals(v.getPlaca()))
                .findFirst();
    }

    @Override
    public List<Veiculo> findByClienteId(Long clienteId) {
        List<Veiculo> list = new ArrayList<>();
        for (Veiculo v : store.values()) {
            if (Objects.equals(clienteId, v.getClienteId())) {
                list.add(v);
            }
        }
        return list;
    }
}
