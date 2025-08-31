package br.com.oficina.usecases.produto;

import br.com.oficina.domain.Produto;
import br.com.oficina.ports.ProdutoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarProdutosPorNomeUseCase {
    private final ProdutoRepository repo;
    public BuscarProdutosPorNomeUseCase(ProdutoRepository repo) { this.repo = repo; }
    public List<Produto> executar(String nome) { return repo.findByNomeContaining(nome); }
}
