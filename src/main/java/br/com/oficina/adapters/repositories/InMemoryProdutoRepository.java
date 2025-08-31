package br.com.oficina.adapters.repositories;

import br.com.oficina.domain.CategoriaProduto;
import br.com.oficina.domain.Produto;
import br.com.oficina.ports.ProdutoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
@Profile("dev")
public class InMemoryProdutoRepository implements ProdutoRepository {

    private final Map<Long, Produto> db = new HashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public Produto save(Produto p) {
        if (p.getId() == null) {
            long id = seq.incrementAndGet();
            Produto novo = new Produto(id, p.getNomeProduto(), p.getDescricaoProduto(), p.getCategoria(), p.isAtivo(), p.getPrecoFinalVenda());
            db.put(id, novo);
            return novo;
        } else {
            db.put(p.getId(), p);
            return p;
        }
    }

    @Override
    public Optional<Produto> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Produto> findAll() {
        return db.values().stream().filter(Produto::isAtivo)
                .sorted(Comparator.comparing(Produto::getNomeProduto)).collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(Long id) {
        Produto p = db.get(id);
        if (p == null) return false;
        Produto des = p.desativar();
        db.put(id, des);
        return true;
    }

    @Override
    public List<Produto> findByCategoria(CategoriaProduto categoria) {
        return db.values().stream().filter(Produto::isAtivo)
                .filter(p -> p.getCategoria() == categoria)
                .sorted(Comparator.comparing(Produto::getNomeProduto)).collect(Collectors.toList());
    }

    @Override
    public List<Produto> findByNomeContaining(String nomeLike) {
        String q = nomeLike == null ? "" : nomeLike.toLowerCase();
        return db.values().stream().filter(Produto::isAtivo)
                .filter(p -> p.getNomeProduto().toLowerCase().contains(q))
                .sorted(Comparator.comparing(Produto::getNomeProduto)).collect(Collectors.toList());
    }
}
