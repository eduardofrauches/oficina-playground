package br.com.oficina.adapters.controllers;

import br.com.oficina.adapters.controllers.dto.*;
import br.com.oficina.adapters.controllers.mappers.ClienteDtoMapper;
import br.com.oficina.ports.ClienteRepository;
import br.com.oficina.usecases.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

// Swagger / OpenAPI
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes (CRUD + PATCH).")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final CadastrarClienteUseCase cadastrar;
    private final BuscarClienteUseCase buscar;
    private final ListarClientesUseCase listar;
    private final AtualizarClienteUseCase atualizar;
    private final RemoverClienteUseCase remover;

    public ClienteController(ClienteRepository repository) {
        this.cadastrar = new CadastrarClienteUseCase(repository);
        this.buscar    = new BuscarClienteUseCase(repository);
        this.listar    = new ListarClientesUseCase(repository);
        this.atualizar = new AtualizarClienteUseCase(repository);
        this.remover   = new RemoverClienteUseCase(repository);
    }

    @Operation(
        summary = "Criar cliente",
        description = "Cria um novo cliente. Campos obrigatórios: nome, email e telefone.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = ClienteRequest.class),
                examples = {
                    @ExampleObject(
                        name = "Mínimo",
                        value = "{\"nome\":\"Fulano\",\"email\":\"fulano@ex.com\",\"telefone\":\"11999999999\"}"
                    ),
                    @ExampleObject(
                        name = "Completo",
                        value = "{\"nome\":\"Fulano\",\"email\":\"fulano@ex.com\",\"telefone\":\"11999999999\",\"cpf\":\"12345678900\",\"logradouro\":\"Rua A\",\"numero\":\"123\",\"bairro\":\"Centro\",\"cidade\":\"São Paulo\",\"estado\":\"SP\",\"cep\":\"01000-000\",\"dataNascimento\":\"1990-05-10\",\"observacao\":\"VIP\"}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso",
                content = @Content(schema = @Schema(implementation = ClienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"status\":400,\"error\":\"Bad Request\",\"message\":\"Erro de validação\",\"fields\":{\"nome\":\"nome é obrigatório\"}}")))
        }
    )
    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@Valid @RequestBody ClienteRequest body) {
        var dominio = ClienteDtoMapper.toDomainForCreate(body);
        var salvo = cadastrar.executar(dominio);
        return ResponseEntity.ok(ClienteDtoMapper.toResponse(salvo));
    }

    @Operation(
        summary = "Buscar cliente por ID",
        description = "Retorna um cliente pelo seu identificador.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Encontrado",
                content = @Content(schema = @Schema(implementation = ClienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id) {
        return buscar.executar(id)
                .map(ClienteDtoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Listar clientes",
        description = "Retorna a lista de clientes."
    )
    @GetMapping
    public List<ClienteResponse> listarTodos() {
        return listar.executar().stream()
                .map(ClienteDtoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(
        summary = "Atualizar cliente (PUT)",
        description = "Atualização completa do cliente (sobrescreve os campos). Para alteração parcial use PATCH.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = ClienteRequest.class),
                examples = @ExampleObject(
                    name = "Atualização simples",
                    value = "{\"nome\":\"Fulano da Silva\",\"email\":\"fulano.silva@ex.com\",\"telefone\":\"11999999999\",\"ativo\":true}"
                )
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado",
                content = @Content(schema = @Schema(implementation = ClienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequest body) {
        var existenteOpt = buscar.executar(id);
        if (existenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var existente = existenteOpt.get();
        var dominio = ClienteDtoMapper.toDomainForUpdate(id, body, existente.getDataCadastro(), existente.isAtivo());
        var salvo = atualizar.executar(dominio);
        return ResponseEntity.ok(ClienteDtoMapper.toResponse(salvo));
    }

    @Operation(
        summary = "Atualização parcial (PATCH)",
        description = "Altera apenas os campos enviados no corpo. Campos ausentes são preservados; string vazia (\"\") limpa o campo (null).",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                schema = @Schema(implementation = ClientePatchRequest.class),
                examples = {
                    @ExampleObject(
                        name = "Trocar nome e email",
                        value = "{\"nome\":\"Fulano da Silva\",\"email\":\"fulano.silva@ex.com\"}"
                    ),
                    @ExampleObject(
                        name = "Limpar observação e ativar",
                        value = "{\"observacao\":\"\",\"ativo\":true}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado parcialmente",
                content = @Content(schema = @Schema(implementation = ClienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação")
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponse> patch(@PathVariable Long id, @Valid @RequestBody ClientePatchRequest body) {
        var existenteOpt = buscar.executar(id);
        if (existenteOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var existente = existenteOpt.get();
        var merged = ClienteDtoMapper.mergeForPatch(existente, body);
        var salvo = atualizar.executar(merged);
        return ResponseEntity.ok(ClienteDtoMapper.toResponse(salvo));
    }

    @Operation(
        summary = "Remover cliente",
        description = "Remove o cliente pelo identificador.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Removido"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return remover.executar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
