package br.com.oficina.ports;

import br.com.oficina.domain.Veiculo;

import java.util.List;
import java.util.Optional;

public interface VeiculoRepository {
    Veiculo save(Veiculo v);
    Optional<Veiculo> findById(Long id);
    List<Veiculo> findAll();
    boolean deleteById(Long id);
    Optional<Veiculo> findByPlaca(String placa);
    List<Veiculo> findByClienteId(Long clienteId);
}
