package br.com.oficina.ports;

import br.com.oficina.domain.Cliente;
import java.util.*;

public interface ClienteRepository {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(String id);
    List<Cliente> findAll();
}
