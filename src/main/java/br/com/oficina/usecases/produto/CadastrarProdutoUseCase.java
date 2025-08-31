package br.com.oficina.usecases.produto;

import br.com.oficina.domain.Produto;
import br.com.oficina.ports.ProdutoRepository;
import org.springframework.stereotype.Component;

@Component
public class CadastrarProdutoUseCase {
    private final ProdutoRepository repo;
    public CadastrarProdutoUseCase(ProdutoRepository repo) { this.repo = repo; }
    public Produto executar(Produto produto) { return repo.save(produto); }
}
