package br.com.oficina;

import br.com.oficina.adapters.repositories.InMemoryClienteRepository;
import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;
import br.com.oficina.usecases.*;

import java.time.LocalDate;
import java.util.List;

public class ClienteCrudMain {

    static void check(String name, boolean condition) {
        if (!condition) throw new AssertionError("Falhou: " + name);
        System.out.println("OK: " + name);
    }

    public static void main(String[] args) {
        var repo = new InMemoryClienteRepository();

        var cadastrar = new CadastrarClienteUseCase(repo);
        var buscar    = new BuscarClienteUseCase(repo);
        var listar    = new ListarClientesUseCase(repo);
        var atualizar = new AtualizarClienteUseCase(repo);
        var remover   = new RemoverClienteUseCase(repo);

        // Endereço opcional
        var end = new Endereco("Rua A", "123", null, "Centro", "São Paulo", "SP", "01000-000");

        // CREATE
        Cliente c1 = Cliente.novo(
                "Ana",                       // nome
                "ana@example.com",           // email
                "11999999999",               // telefone
                "12345678900",               // cpf
                null,                        // cnpj
                end,                         // endereco
                LocalDate.of(1990, 1, 1),    // dataNascimento
                "Observação qualquer"        // observacao
        );
        c1 = cadastrar.executar(c1);
        Long id = c1.getId();
        System.out.println("[CREATE] " + c1);

        // READ
        Cliente found = buscar.executar(id).orElseThrow();
        System.out.println("[READ]   " + found);

        // LIST
        List<Cliente> lista = listar.executar();
        System.out.println("[LIST]   " + lista);

        // UPDATE (id deve ser reaproveitado)
        Cliente c1Atualizado = Cliente.novo(
                "Ana Souza",
                "ana.souza@example.com",
                "11999999999",
                "12345678900",
                null,
                end,
                LocalDate.of(1990, 1, 1),
                "VIP"
        );
        c1Atualizado.definirId(id);
        atualizar.executar(c1Atualizado);
        Cliente updated = buscar.executar(id).orElseThrow();
        System.out.println("[UPDATE] " + updated);
        check("nome atualizado", "Ana Souza".equals(updated.getNome()));

        // DELETE
        boolean deletou = remover.executar(id);
        System.out.println("[DELETE] id=" + id + " -> " + deletou);

        // LIST final
        System.out.println("[LIST]   " + listar.executar());
    }
}
