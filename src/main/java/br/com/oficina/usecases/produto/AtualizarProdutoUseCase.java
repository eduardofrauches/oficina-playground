package br.com.oficina.usecases.produto;

import br.com.oficina.domain.Produto;
import br.com.oficina.ports.ProdutoRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarProdutoUseCase {
    private final ProdutoRepository repo;
    public AtualizarProdutoUseCase(ProdutoRepository repo) { this.repo = repo; }
    public Produto executar(Long id, Produto atualizado) {
        Produto atual = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        // preserva ativo atual
        Produto novo = new Produto(atual.getId(),
                atualizado.getNomeProduto(),
                atualizado.getDescricaoProduto(),
                atualizado.getCategoria(),
                atual.isAtivo(),
                atualizado.getPrecoFinalVenda());
        return repo.save(novo);
    }
}
