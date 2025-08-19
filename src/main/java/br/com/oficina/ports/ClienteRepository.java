package br.com.oficina.ports;

import br.com.oficina.domain.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente save(Cliente cliente);                      // cria/atualiza e retorna com ID
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    boolean deleteById(Long id);
}
