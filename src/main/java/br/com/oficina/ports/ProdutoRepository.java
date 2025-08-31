package br.com.oficina.ports;

import br.com.oficina.domain.CategoriaProduto;
import br.com.oficina.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    Produto save(Produto p);
    Optional<Produto> findById(Long id);
    List<Produto> findAll(); // apenas ativos
    boolean deleteById(Long id); // soft delete: marcar ativo=false
    List<Produto> findByCategoria(CategoriaProduto categoria);
    List<Produto> findByNomeContaining(String nomeLike);
}
