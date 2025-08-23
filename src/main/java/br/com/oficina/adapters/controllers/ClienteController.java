package br.com.oficina.adapters.controllers;

import br.com.oficina.adapters.controllers.dto.ClienteDto;
import br.com.oficina.domain.Cliente;
import br.com.oficina.usecases.cliente.AtualizarClienteUseCase;
import br.com.oficina.usecases.cliente.BuscarClienteUseCase;
import br.com.oficina.usecases.cliente.CadastrarClienteUseCase;
import br.com.oficina.usecases.cliente.ListarClientesUseCase;
import br.com.oficina.usecases.cliente.RemoverClienteUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "CRUD de clientes")
public class ClienteController {

    private final CadastrarClienteUseCase cadastrar;
    private final BuscarClienteUseCase buscar;
    private final ListarClientesUseCase listar;
    private final AtualizarClienteUseCase atualizar;
    private final RemoverClienteUseCase remover;

    public ClienteController(CadastrarClienteUseCase cadastrar,
                             BuscarClienteUseCase buscar,
                             ListarClientesUseCase listar,
                             AtualizarClienteUseCase atualizar,
                             RemoverClienteUseCase remover) {
        this.cadastrar = cadastrar;
        this.buscar = buscar;
        this.listar = listar;
        this.atualizar = atualizar;
        this.remover = remover;
    }

    // LIST
    @GetMapping
    @Operation(summary = "Listar clientes")
    public ResponseEntity<List<ClienteDto>> list() {
        List<ClienteDto> out = listar.execute()
                .stream()
                .map(ClienteDto::fromDomain)
                .toList();
        return ResponseEntity.ok(out);
    }

    // GET by ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID")
    public ResponseEntity<ClienteDto> getById(@PathVariable Long id) {
        Optional<Cliente> opt = buscar.execute(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ClienteDto.fromDomain(opt.get()));
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Criar cliente")
    public ResponseEntity<ClienteDto> create(@RequestBody ClienteDto body) {
        // dataCadastro pode vir nula no POST — atribuímos agora (ou deixe para o domínio/repositório atribuir)
        if (body.dataCadastro == null) {
            body.dataCadastro = LocalDateTime.now();
        }
        Cliente salvo = cadastrar.execute(ClienteDto.toDomain(body));
        ClienteDto resp = ClienteDto.fromDomain(salvo);
        return ResponseEntity
                .created(URI.create("/clientes/" + resp.id))
                .body(resp);
    }

    // UPDATE (full)
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente (substituição completa)")
    public ResponseEntity<ClienteDto> update(@PathVariable Long id, @RequestBody ClienteDto body) {
        Optional<Cliente> existente = buscar.execute(id);
        if (existente.isEmpty()) return ResponseEntity.notFound().build();

        // preservar dataCadastro original
        body.id = id;
        body.dataCadastro = existente.get().getDataCadastro();

        Cliente atualizadoDom = ClienteDto.toDomain(body);
        Cliente result = atualizar.execute(atualizadoDom);
        return ResponseEntity.ok(ClienteDto.fromDomain(result));
    }

    // PATCH (merge: só troca campos não nulos do body)
    @PatchMapping("/{id}")
    @Operation(summary = "Atualização parcial (merge)")
    public ResponseEntity<ClienteDto> patch(@PathVariable Long id, @RequestBody ClienteDto parcial) {
        Optional<Cliente> existente = buscar.execute(id);
        if (existente.isEmpty()) return ResponseEntity.notFound().build();

        ClienteDto base = ClienteDto.fromDomain(existente.get());
        // aplica somente não nulos
        if (parcial.nome != null) base.nome = parcial.nome;
        if (parcial.cpf != null) base.cpf = parcial.cpf;
        if (parcial.cnpj != null) base.cnpj = parcial.cnpj;
        if (parcial.email != null) base.email = parcial.email;
        if (parcial.telefone != null) base.telefone = parcial.telefone;

        if (parcial.logradouro != null) base.logradouro = parcial.logradouro;
        if (parcial.numero != null) base.numero = parcial.numero;
        if (parcial.complemento != null) base.complemento = parcial.complemento;
        if (parcial.bairro != null) base.bairro = parcial.bairro;
        if (parcial.cidade != null) base.cidade = parcial.cidade;
        if (parcial.estado != null) base.estado = parcial.estado;
        if (parcial.cep != null) base.cep = parcial.cep;

        if (parcial.dataNascimento != null) base.dataNascimento = parcial.dataNascimento;
        if (parcial.observacao != null) base.observacao = parcial.observacao;
        if (parcial.ativo != null) base.ativo = parcial.ativo;

        // preservar id e dataCadastro
        base.id = id;
        base.dataCadastro = existente.get().getDataCadastro();

        Cliente result = atualizar.execute(ClienteDto.toDomain(base));
        return ResponseEntity.ok(ClienteDto.fromDomain(result));
    }

    // DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean ok = remover.execute(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
