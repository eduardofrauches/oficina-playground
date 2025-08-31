package br.com.oficina.usecases.produto;

import br.com.oficina.domain.Produto;
import br.com.oficina.ports.ProdutoRepository;
import org.springframework.stereotype.Component;

@Component
public class BuscarProdutoUseCase {
    private final ProdutoRepository repo;
    public BuscarProdutoUseCase(ProdutoRepository repo) { this.repo = repo; }
    public Produto executar(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
    }
}
