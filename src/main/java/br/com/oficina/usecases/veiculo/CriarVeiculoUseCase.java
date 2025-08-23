package br.com.oficina.usecases.veiculo;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;
import org.springframework.stereotype.Component;

@Component
public class CriarVeiculoUseCase {

    private final VeiculoRepository repo;

    public CriarVeiculoUseCase(VeiculoRepository repo) {
        this.repo = repo;
    }

    public Veiculo executar(Veiculo veiculo) {
        // como a porta só tem findByPlaca(String), garanta normalização no mapper (toUpperCase/trim)
        repo.findByPlaca(veiculo.getPlaca()).ifPresent(v -> {
            throw new IllegalArgumentException("Placa já cadastrada: " + v.getPlaca());
        });
        return repo.save(veiculo);
    }
}
