package br.com.oficina.usecases.produto;

import br.com.oficina.ports.ProdutoRepository;
import org.springframework.stereotype.Component;

@Component
public class RemoverProdutoUseCase {
    private final ProdutoRepository repo;
    public RemoverProdutoUseCase(ProdutoRepository repo) { this.repo = repo; }
    public boolean executar(Long id) { return repo.deleteById(id); }
}
