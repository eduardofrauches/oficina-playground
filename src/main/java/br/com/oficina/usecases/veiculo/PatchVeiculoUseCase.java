package br.com.oficina.usecases.veiculo;

import org.springframework.stereotype.Component;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

import java.util.NoSuchElementException;

/**
 * Atualização parcial (PATCH) de veículo.
 * A mescla dos campos normalmente é feita no Controller/DTO mapper.
 */
@Component
public class PatchVeiculoUseCase {
    private final VeiculoRepository repo;

    public PatchVeiculoUseCase(VeiculoRepository repo) {
        this.repo = repo;
    }

    public Veiculo executar(Long id, Veiculo mesclado) {
        repo.findById(id).orElseThrow(() -> new NoSuchElementException("veículo não encontrado"));
        if (mesclado.getId() == null || !id.equals(mesclado.getId())) {
            mesclado.definirId(id);
        }
        return repo.save(mesclado);
    }
}
