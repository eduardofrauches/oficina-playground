package br.com.oficina.adapters.controllers;

import br.com.oficina.adapters.controllers.dto.UsuarioRequest;
import br.com.oficina.adapters.controllers.dto.UsuarioResponse;
import br.com.oficina.domain.Usuario;
import br.com.oficina.usecases.usuario.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path="/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Usuários", description="CRUD de usuários (soft delete)")
public class UsuarioController {

    private final CriarUsuarioUseCase criar;
    private final BuscarUsuarioUseCase buscar;
    private final ListarUsuariosUseCase listar;
    private final AtualizarUsuarioUseCase atualizar;
    private final PatchUsuarioUseCase patch;
    private final ExcluirUsuarioUseCase excluir;

    public UsuarioController(CriarUsuarioUseCase criar,
                             BuscarUsuarioUseCase buscar,
                             ListarUsuariosUseCase listar,
                             AtualizarUsuarioUseCase atualizar,
                             PatchUsuarioUseCase patch,
                             ExcluirUsuarioUseCase excluir) {
        this.criar = criar;
        this.buscar = buscar;
        this.listar = listar;
        this.atualizar = atualizar;
        this.patch = patch;
        this.excluir = excluir;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Cria usuário")
    public ResponseEntity<UsuarioResponse> criar(@RequestBody UsuarioRequest req){
        Usuario salvo = criar.executar(req.toDomainNovo());
        return ResponseEntity.created(URI.create("/usuarios/" + salvo.getId()))
                .body(UsuarioResponse.from(salvo));
    }

    @GetMapping("/{id}")
    @Operation(summary="Busca usuário por id")
    public ResponseEntity<UsuarioResponse> obter(@PathVariable Long id){
        return buscar.porId(id)
                .map(UsuarioResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary="Lista usuários; filtros opcionais: roleId ou nome")
    public List<UsuarioResponse> listar(@RequestParam(required = false) Long roleId,
                                        @RequestParam(required = false, name = "nome") String nome) {
        if (roleId != null) {
            return listar.porRole(roleId).stream().map(UsuarioResponse::from).toList();
        }
        if (nome != null && !nome.isBlank()) {
            return listar.porNome(nome).stream().map(UsuarioResponse::from).toList();
        }
        return listar.todos().stream().map(UsuarioResponse::from).toList();
    }

    @PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary="Atualiza usuário (substituição total; preserva senha/ativo se não enviados)")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody UsuarioRequest req){
        Usuario atual = buscar.porId(id).orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
        Usuario atualizado = req.toDomainAtualizacao(id, atual);
        return ResponseEntity.ok(UsuarioResponse.from(atualizar.executar(atualizado)));
    }

    @PatchMapping("/{id}/nome")
    @Operation(summary="Atualiza nome")
    public ResponseEntity<UsuarioResponse> patchNome(@PathVariable Long id, @RequestBody String nome){
        return ResponseEntity.ok(UsuarioResponse.from(patch.atualizarNome(id, nome)));
    }

    @PatchMapping("/{id}/email")
    @Operation(summary="Atualiza email")
    public ResponseEntity<UsuarioResponse> patchEmail(@PathVariable Long id, @RequestBody String email){
        return ResponseEntity.ok(UsuarioResponse.from(patch.atualizarEmail(id, email)));
    }

    @PatchMapping("/{id}/username")
    @Operation(summary="Atualiza username")
    public ResponseEntity<UsuarioResponse> patchUsername(@PathVariable Long id, @RequestBody String username){
        return ResponseEntity.ok(UsuarioResponse.from(patch.atualizarUsername(id, username)));
    }

    @PatchMapping("/{id}/password")
    @Operation(summary="Atualiza senha (senha em texto; o sistema hasheia)")
    public ResponseEntity<UsuarioResponse> patchPassword(@PathVariable Long id, @RequestBody String password){
        return ResponseEntity.ok(UsuarioResponse.from(patch.atualizarPassword(id, password)));
    }

    @PatchMapping("/{id}/role/{roleId}")
    @Operation(summary="Atualiza role")
    public ResponseEntity<UsuarioResponse> patchRole(@PathVariable Long id, @PathVariable Long roleId){
        return ResponseEntity.ok(UsuarioResponse.from(patch.atualizarRole(id, roleId)));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary="Reativa (ativo=true)")
    public ResponseEntity<UsuarioResponse> ativar(@PathVariable Long id){
        return ResponseEntity.ok(UsuarioResponse.from(patch.reativar(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary="Soft delete (ativo=false)")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        return excluir.executar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
