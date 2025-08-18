package br.com.oficina.ports;

import br.com.oficina.domain.Cliente;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de Cliente.
 * Define as operações de CRUD que qualquer repositório deve implementar.
 */
public interface ClienteRepository {
    Cliente save(Cliente cliente);              // Create
    Optional<Cliente> findById(String id);      // Read (buscar por id)
    List<Cliente> findAll();                    // Read (listar todos)
    Cliente update(Cliente cliente);            // Update
    boolean deleteById(String id);              // Delete
}
