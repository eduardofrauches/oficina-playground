package br.com.oficina.adapters.controllers;

import br.com.oficina.adapters.controllers.dto.VeiculoDtoMapper;
import br.com.oficina.adapters.controllers.dto.VeiculoRequest;
import br.com.oficina.adapters.controllers.dto.VeiculoResponse;
import br.com.oficina.domain.Veiculo;

import br.com.oficina.usecases.veiculo.BuscarVeiculoUseCase;
import br.com.oficina.usecases.veiculo.ListarVeiculosUseCase;
import br.com.oficina.usecases.veiculo.CriarVeiculoUseCase;
import br.com.oficina.usecases.veiculo.AtualizarVeiculoUseCase;
import br.com.oficina.usecases.veiculo.PatchVeiculoUseCase;
import br.com.oficina.usecases.veiculo.ExcluirVeiculoUseCase;
import br.com.oficina.usecases.veiculo.BuscarVeiculoPorPlacaUseCase;
import br.com.oficina.usecases.veiculo.ListarVeiculosPorClienteUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/veiculos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Veículos", description = "CRUD de veículos")
public class VeiculoController {

    private final CriarVeiculoUseCase criar;
    private final BuscarVeiculoUseCase buscarPorId;
    private final ListarVeiculosUseCase listar;
    private final AtualizarVeiculoUseCase atualizar;
    private final PatchVeiculoUseCase patch;
    private final ExcluirVeiculoUseCase excluir;
    private final BuscarVeiculoPorPlacaUseCase buscarPorPlaca;
    private final ListarVeiculosPorClienteUseCase listarPorCliente;

    public VeiculoController(CriarVeiculoUseCase criar,
                             BuscarVeiculoUseCase buscarPorId,
                             ListarVeiculosUseCase listar,
                             AtualizarVeiculoUseCase atualizar,
                             PatchVeiculoUseCase patch,
                             ExcluirVeiculoUseCase excluir,
                             BuscarVeiculoPorPlacaUseCase buscarPorPlaca,
                             ListarVeiculosPorClienteUseCase listarPorCliente) {
        this.criar = criar;
        this.buscarPorId = buscarPorId;
        this.listar = listar;
        this.atualizar = atualizar;
        this.patch = patch;
        this.excluir = excluir;
        this.buscarPorPlaca = buscarPorPlaca;
        this.listarPorCliente = listarPorCliente;
    }

    @Operation(summary = "Listar todos os veículos")
    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> listar() {
        var resp = listar.executar().stream()
                .map(VeiculoDtoMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Obter veículo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Encontrado"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> obter(@PathVariable Long id) {
        return buscarPorId.executar(id)
                .map(VeiculoDtoMapper::fromDomain)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar veículo pela placa (exata)")
    @GetMapping("/placa/{placa}")
    public ResponseEntity<VeiculoResponse> buscarPorPlacaExata(@PathVariable String placa) {
        return buscarPorPlaca.executarExata(placa)
                .map(VeiculoDtoMapper::fromDomain)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar veículos por placa (parcial) — use ?q=",
            description = "Retorna lista quando a placa contém o termo informado (case-insensitive). Ex.: /veiculos/placa?q=ABC")
    @GetMapping("/placa")
    public ResponseEntity<List<VeiculoResponse>> buscarPorPlacaParcial(@RequestParam("q") String termo) {
        String t = termo == null ? "" : termo.trim();
        if (t.isEmpty()) return ResponseEntity.badRequest().build();
        var encontrados = buscarPorPlaca.executarParcial(t).stream()
                .map(VeiculoDtoMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(encontrados);
    }

    @Operation(summary = "Listar veículos por cliente")
    @GetMapping(path = "/clientes/{clienteId}")
    public ResponseEntity<List<VeiculoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        var lista = listarPorCliente.executar(clienteId).stream()
                .map(VeiculoDtoMapper::fromDomain)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(
            summary = "Criar veículo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VeiculoRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "placa": "ABC1D23",
                                      "marca": "Fiat",
                                      "modelo": "Argo",
                                      "ano": 2020,
                                      "cor": "Prata",
                                      "observacoes": "Cliente prefere atendimento aos sábados",
                                      "clienteId": 1,
                                      "ativo": true
                                    }
                                    """)
                    )
            )
    )
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VeiculoResponse> criar(@RequestBody VeiculoRequest req) {
        Veiculo created = criar.executar(VeiculoDtoMapper.toDomain(req, null));
        VeiculoResponse resp = VeiculoDtoMapper.fromDomain(created);
        return ResponseEntity.created(URI.create("/veiculos/" + resp.id)).body(resp);
    }

    @Operation(summary = "Atualizar veículo (substituição total)")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VeiculoResponse> atualizar(@PathVariable Long id, @RequestBody VeiculoRequest req) {
        Veiculo atualizado = atualizar.executar(id, VeiculoDtoMapper.toDomain(req, id));
        return ResponseEntity.ok(VeiculoDtoMapper.fromDomain(atualizado));
    }

    @Operation(
            summary = "Atualização parcial (merge)",
            description = "Somente os campos enviados no body são atualizados. Os demais permanecem inalterados.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = VeiculoRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "cor": "Vermelho",
                                      "observacoes": "Alterado via PATCH"
                                    }
                                    """)
                    )
            )
    )
    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VeiculoResponse> patch(@PathVariable Long id, @RequestBody VeiculoRequest req) {
        return buscarPorId.executar(id)
                .map(original -> {
                    Veiculo merged = VeiculoDtoMapper.merge(original, req);
                    Veiculo salvo = patch.executar(id, merged);
                    return ResponseEntity.ok(VeiculoDtoMapper.fromDomain(salvo));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Excluir veículo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Excluído"),
            @ApiResponse(responseCode = "404", description = "Não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean removed = excluir.executar(id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
