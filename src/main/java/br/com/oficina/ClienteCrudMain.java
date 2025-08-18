package br.com.oficina;

import br.com.oficina.adapters.repositories.InMemoryClienteRepository;
import br.com.oficina.domain.Cliente;
import br.com.oficina.usecases.*;

import java.util.UUID;

public class ClienteCrudMain {
    public static void main(String[] args) {
        var repo = new InMemoryClienteRepository();

        var cadastrar = new CadastrarClienteUseCase(repo);
        var buscar    = new BuscarClienteUseCase(repo);
        var listar    = new ListarClientesUseCase(repo);
        var atualizar = new AtualizarClienteUseCase(repo);
        var remover   = new RemoverClienteUseCase(repo);

        String id = UUID.randomUUID().toString();

        // CREATE
        var c1 = new Cliente(id, "Ana Silva", "ana@example.com");
        cadastrar.executar(c1);
        System.out.println("[CREATE] " + c1);

        // READ (by id)
        var encontrado = buscar.executar(id).orElseThrow();
        System.out.println("[READ]   " + encontrado);

        // LIST
        System.out.println("[LIST]   " + listar.executar());

        // UPDATE
        var c1Atualizado = new Cliente(id, "Ana Souza", "ana.souza@example.com");
        atualizar.executar(c1Atualizado);
        System.out.println("[UPDATE] " + buscar.executar(id).orElseThrow());

        // DELETE
        boolean deletou = remover.executar(id);
        System.out.println("[DELETE] id=" + id + " -> " + deletou);

        // LIST após excluir
        System.out.println("[LIST]   " + listar.executar());
    }
}
