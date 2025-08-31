package br.com.oficina.usecases.produto;

import br.com.oficina.domain.CategoriaProduto;
import br.com.oficina.domain.Produto;
import br.com.oficina.ports.ProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarProdutosPorCategoriaUseCase {
    private final ProdutoRepository repo;
    public ListarProdutosPorCategoriaUseCase(ProdutoRepository repo) { this.repo = repo; }
    public List<Produto> executar(CategoriaProduto categoria) { return repo.findByCategoria(categoria); }
}
