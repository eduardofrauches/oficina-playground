package br.com.oficina.adapters.controllers;

import br.com.oficina.adapters.controllers.dto.ProdutoRequest;
import br.com.oficina.adapters.controllers.dto.ProdutoResponse;
import br.com.oficina.adapters.controllers.dto.ProdutoDtoMapper;
import br.com.oficina.domain.CategoriaProduto;
import br.com.oficina.domain.Produto;
import br.com.oficina.usecases.produto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Catálogo de Produtos", description = "Cadastro, busca e gerenciamento de produtos (peças/insumos).")
public class ProdutoController {

    private final CadastrarProdutoUseCase cadastrar;
    private final BuscarProdutoUseCase buscarPorId;
    private final AtualizarProdutoUseCase atualizar;
    private final RemoverProdutoUseCase remover;
    private final ListarProdutosUseCase listar;
    private final ListarProdutosPorCategoriaUseCase listarPorCategoria;
    private final BuscarProdutosPorNomeUseCase buscarPorNome;

    public ProdutoController(CadastrarProdutoUseCase cadastrar,
                             BuscarProdutoUseCase buscarPorId,
                             AtualizarProdutoUseCase atualizar,
                             RemoverProdutoUseCase remover,
                             ListarProdutosUseCase listar,
                             ListarProdutosPorCategoriaUseCase listarPorCategoria,
                             BuscarProdutosPorNomeUseCase buscarPorNome) {
        this.cadastrar = cadastrar;
        this.buscarPorId = buscarPorId;
        this.atualizar = atualizar;
        this.remover = remover;
        this.listar = listar;
        this.listarPorCategoria = listarPorCategoria;
        this.buscarPorNome = buscarPorNome;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cadastrar novo produto")
    public ResponseEntity<ProdutoResponse> criar(@RequestBody ProdutoRequest req){
        Produto novo = Produto.novo(req.nomeProduto, req.descricaoProduto, req.categoria, req.precoFinalVenda);
        Produto salvo = cadastrar.executar(novo);
        return ResponseEntity.created(URI.create("/produtos/" + salvo.getId())).body(ProdutoDtoMapper.toResponse(salvo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ProdutoResponse buscarPorId(@PathVariable Long id){
        return ProdutoDtoMapper.toResponse(buscarPorId.executar(id));
    }

    @PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar produto por ID")
    public ProdutoResponse atualizar(@PathVariable Long id, @RequestBody ProdutoRequest req){
        Produto novo = new Produto(id, req.nomeProduto, req.descricaoProduto, req.categoria, true, req.precoFinalVenda);
        return ProdutoDtoMapper.toResponse(atualizar.executar(id, novo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover (soft delete) produto por ID")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        boolean ok = remover.executar(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os produtos ativos")
    public List<ProdutoResponse> listarTodos(){
        return listar.executar().stream().map(ProdutoDtoMapper::toResponse).toList();
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar produtos por categoria (PECA | INSUMO)")
    public List<ProdutoResponse> listarPorCategoria(@PathVariable CategoriaProduto categoria){
        return listarPorCategoria.executar(categoria).stream().map(ProdutoDtoMapper::toResponse).toList();
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar produtos por nome (contains, case-insensitive)")
    public List<ProdutoResponse> buscarPorNome(@RequestParam("nome") String nome){
        return buscarPorNome.executar(nome).stream().map(ProdutoDtoMapper::toResponse).toList();
    }
}
