package br.com.oficina;

import br.com.oficina.adapters.repositories.jpa.JpaClienteRepository;
import br.com.oficina.domain.Cliente;
import br.com.oficina.usecases.*;

import java.util.UUID;

public class JpaClienteMain {
    public static void main(String[] args) {
        var repo = new JpaClienteRepository();
        var cadastrar = new CadastrarClienteUseCase(repo);
        var buscar    = new BuscarClienteUseCase(repo);
        var listar    = new ListarClientesUseCase(repo);
        var atualizar = new AtualizarClienteUseCase(repo);
        var remover   = new RemoverClienteUseCase(repo);

        String id = UUID.randomUUID().toString();

        var c = new Cliente(id, "Maria", "maria@example.com");
        cadastrar.executar(c);
        System.out.println("[CREATE] " + c);

        System.out.println("[READ]   " + buscar.executar(id).orElseThrow());
        System.out.println("[LIST]   " + listar.executar());

        var c2 = new Cliente(id, "Maria Souza", "maria.souza@example.com");
        atualizar.executar(c2);
        System.out.println("[UPDATE] " + buscar.executar(id).orElseThrow());

        System.out.println("[DELETE] " + remover.executar(id));
        System.out.println("[LIST]   " + listar.executar());

        // Fechar fábrica JPA (boa prática em app standalone)
        br.com.oficina.adapters.repositories.jpa.JpaUtil.close();
    }
}
