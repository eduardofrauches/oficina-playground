package br.com.oficina.adapters.repositories;

import br.com.oficina.domain.Servico;
import br.com.oficina.ports.ServicoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("dev")
public class InMemoryServicoRepository implements ServicoRepository {

    private final Map<Long, Servico> store = new HashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Servico salvar(Servico servico) {
        Long id = servico.getId();
        if (id == null) {
            id = seq.incrementAndGet();
            Servico novo = Servico.reconstituir(
                    id, servico.getNome(), servico.getDescricao(), servico.getPreco(),
                    servico.getTempoEstimado(), servico.getCategoria(), servico.isAtivo(),
                    null, null
            );
            store.put(id, novo);
            return novo;
        } else {
            store.put(id, servico);
            return servico;
        }
    }

    @Override
    public Optional<Servico> buscarPorId(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Servico> listarTodos() {
        return store.values().stream().filter(Servico::isAtivo).sorted(Comparator.comparing(Servico::getNome)).toList();
    }

    @Override
    public List<Servico> buscarPorPalavras(String termos) {
        if (termos == null || termos.isBlank()) return listarTodos();
        String t = termos.toLowerCase(Locale.ROOT);
        return store.values().stream()
                .filter(Servico::isAtivo)
                .filter(s -> (s.getNome() + " " + Objects.toString(s.getDescricao(), "")).toLowerCase(Locale.ROOT).contains(t))
                .sorted(Comparator.comparing(Servico::getNome))
                .toList();
    }
}
