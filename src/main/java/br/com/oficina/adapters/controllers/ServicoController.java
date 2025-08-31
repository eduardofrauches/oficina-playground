package br.com.oficina.adapters.controllers;

import br.com.oficina.adapters.controllers.dto.ServicoRequest;
import br.com.oficina.adapters.controllers.dto.ServicoResponse;
import br.com.oficina.usecases.servico.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/servicos")
@Tag(name = "Cadastro e gerenciamento de Serviços")
public class ServicoController {

    private final CadastrarServicoUseCase cadastrar;
    private final BuscarServicoUseCase buscar;
    private final AtualizarServicoUseCase atualizar;
    private final RemoverServicoUseCase remover;
    private final ListarServicosUseCase listar;
    private final BuscarServicosPorPalavrasUseCase buscarPorPalavras;

    public ServicoController(CadastrarServicoUseCase cadastrar,
                             BuscarServicoUseCase buscar,
                             AtualizarServicoUseCase atualizar,
                             RemoverServicoUseCase remover,
                             ListarServicosUseCase listar,
                             BuscarServicosPorPalavrasUseCase buscarPorPalavras) {
        this.cadastrar = cadastrar; this.buscar = buscar; this.atualizar = atualizar;
        this.remover = remover; this.listar = listar; this.buscarPorPalavras = buscarPorPalavras;
    }

    @PostMapping
    public ResponseEntity<ServicoResponse> cadastrar(@RequestBody ServicoRequest req) {
        ServicoResponse resp = cadastrar.executar(req);
        return ResponseEntity.created(URI.create("/servicos/" + resp.id)).body(resp);
    }

    @GetMapping("/{id}")
    public ServicoResponse buscar(@PathVariable Long id) { return buscar.executar(id); }

    @PutMapping("/{id}")
    public ServicoResponse atualizar(@PathVariable Long id, @RequestBody ServicoRequest req) {
        return atualizar.executar(id, req);
    }

    @DeleteMapping("/{id}")
    public ServicoResponse remover(@PathVariable Long id) { return remover.executar(id); }

    @GetMapping
    public List<ServicoResponse> listar() { return listar.executar(); }

    @GetMapping("/search")
    public List<ServicoResponse> buscarPorPalavras(@RequestParam("q") String termos) {
        return buscarPorPalavras.executar(termos);
    }
}
