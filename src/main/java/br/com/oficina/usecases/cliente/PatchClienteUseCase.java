package br.com.oficina.usecases.cliente;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;

import java.util.NoSuchElementException;

/**
 * Atualização parcial (PATCH) de cliente.
  */
public class PatchClienteUseCase {
    private final ClienteRepository repo;

    public PatchClienteUseCase(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente executar(Long id, Cliente mesclado) {
        repo.findById(id).orElseThrow(() -> new NoSuchElementException("cliente não encontrado"));
        if (mesclado.getId() == null || !id.equals(mesclado.getId())) {
            mesclado.definirId(id);
        }
        return repo.save(mesclado);
    }
}
