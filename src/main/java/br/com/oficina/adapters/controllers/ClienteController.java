package br.com.oficina.adapters.controllers;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;
import br.com.oficina.usecases.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final CadastrarClienteUseCase cadastrar;
    private final BuscarClienteUseCase buscar;
    private final ListarClientesUseCase listar;
    private final AtualizarClienteUseCase atualizar;
    private final RemoverClienteUseCase remover;

    public ClienteController(ClienteRepository repository) {
        // Núcleo continua puro; aqui só orquestramos pelos casos de uso
        this.cadastrar = new CadastrarClienteUseCase(repository);
        this.buscar    = new BuscarClienteUseCase(repository);
        this.listar    = new ListarClientesUseCase(repository);
        this.atualizar = new AtualizarClienteUseCase(repository);
        this.remover   = new RemoverClienteUseCase(repository);
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente body) {
        // Se quiser gerar o ID aqui, gere e crie novo Cliente(id, ...)
        return ResponseEntity.ok(cadastrar.executar(body));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable String id) {
        return buscar.executar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(listar.executar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable String id, @RequestBody Cliente body) {
        var atualizado = new Cliente(id, body.getNome(), body.getEmail());
        return ResponseEntity.ok(atualizar.executar(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        return remover.executar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
