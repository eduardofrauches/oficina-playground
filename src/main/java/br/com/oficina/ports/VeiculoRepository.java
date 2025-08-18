package br.com.oficina.ports;

import br.com.oficina.domain.Veiculo;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository {
    Veiculo save(Veiculo veiculo);               // Create
    Optional<Veiculo> findById(String id);       // Read (by id)
    List<Veiculo> findAll();                     // Read (list)
    Veiculo update(Veiculo veiculo);             // Update
    boolean deleteById(String id);               // Delete

    // (Opcional) buscas específicas que você queira no futuro:
    // Optional<Veiculo> findByPlaca(String placa);
}
