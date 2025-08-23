package br.com.oficina.usecases.veiculo;

import org.springframework.stereotype.Component;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;

import java.util.NoSuchElementException;

/**
 * Atualização completa (PUT) de veículo.
 */
@Component
public class AtualizarVeiculoUseCase {
    private final VeiculoRepository repo;

    public AtualizarVeiculoUseCase(VeiculoRepository repo) {
        this.repo = repo;
    }

    public Veiculo executar(Long id, Veiculo completo) {
        repo.findById(id).orElseThrow(() -> new NoSuchElementException("veículo não encontrado"));
        if (completo.getId() == null || !id.equals(completo.getId())) {
            completo.definirId(id);
        }
        return repo.save(completo);
    }

        private String normalizePlaca(String placa) {
        return placa == null ? null : placa.trim().toUpperCase();
    }
}
