package br.com.oficina.usecases.veiculo;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BuscarVeiculoPorPlacaUseCase {

    private final VeiculoRepository repo;

    public BuscarVeiculoPorPlacaUseCase(VeiculoRepository repo) {
        this.repo = repo;
    }

    // exata (porta tem findByPlaca)
    public Optional<Veiculo> executarExata(String placa) {
        if (placa == null) return Optional.empty();
        return repo.findByPlaca(placa.trim());
    }

    // parcial (se a porta não tiver método, faça fallback em memória OU injete o JpaVeiculoRepository helper)
    public List<Veiculo> executarParcial(String termo) {
        String t = termo == null ? "" : termo.trim();
        if (t.isEmpty()) return List.of();

        // Fallback simples (até você expor um método na porta):
        return repo.findAll().stream()
                .filter(v -> v.getPlaca() != null && v.getPlaca().toLowerCase().contains(t.toLowerCase()))
                .toList();

        // Alternativa (se quiser usar o helper do JPA direto):
        // if (repo instanceof br.com.oficina.adapters.repositories.jpa.JpaVeiculoRepository jpa) {
        //     return jpa.findByPlacaContainingIgnoreCaseHelper(t);
        // }
        // return List.of();
    }
}
