package br.com.oficina.adapters.repositories;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

import java.util.*;

public class InMemoryVeiculoRepository implements VeiculoRepository {

    private final Map<String, Veiculo> database = new HashMap<>();

    @Override
    public Veiculo save(Veiculo veiculo) {
        if (veiculo == null) throw new IllegalArgumentException("veiculo não pode ser nulo");
        database.put(veiculo.getId(), veiculo);
        return veiculo;
    }

    @Override
    public Optional<Veiculo> findById(String id) {
        if (id == null || id.isBlank()) return Optional.empty();
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Veiculo> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Veiculo update(Veiculo veiculo) {
        if (veiculo == null) throw new IllegalArgumentException("veiculo não pode ser nulo");
        String id = veiculo.getId();
        if (id == null || id.isBlank() || !database.containsKey(id)) {
            throw new NoSuchElementException("Veículo não encontrado para update: " + id);
        }
        database.put(id, veiculo);
        return veiculo;
    }

    @Override
    public boolean deleteById(String id) {
        if (id == null || id.isBlank()) return false;
        return database.remove(id) != null;
    }
}
