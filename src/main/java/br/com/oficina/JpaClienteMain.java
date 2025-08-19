package br.com.oficina;

import br.com.oficina.adapters.repositories.jpa.JpaClienteRepository;
import br.com.oficina.adapters.repositories.jpa.JpaUtil;
import br.com.oficina.domain.Cliente;
import br.com.oficina.usecases.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Supplier;

public class JpaClienteMain {

    // Helpers simples de “assert”
    static void check(String name, boolean condition) {
        if (condition) {
            System.out.println("✔ " + name);
        } else {
            System.out.println("✘ " + name);
            throw new AssertionError(name);
        }
    }

    static void expectThrows(String name, Class<? extends Throwable> expected, Runnable fn) {
        try {
            fn.run();
            System.out.println("✘ " + name + " (não lançou " + expected.getSimpleName() + ")");
            throw new AssertionError(name);
        } catch (Throwable t) {
            if (expected.isInstance(t) || (t.getCause() != null && expected.isInstance(t.getCause()))) {
                System.out.println("✔ " + name + " (lançou " + expected.getSimpleName() + ")");
            } else {
                System.out.println("✘ " + name + " (lançou " + t.getClass().getSimpleName() + ", esperado " + expected.getSimpleName() + ")");
                throw new AssertionError(name, t);
            }
        }
    }

    public static void main(String[] args) {
        // Repositório JPA e casos de uso
        var repo      = new JpaClienteRepository();
        var cadastrar = new CadastrarClienteUseCase(repo);
        var buscar    = new BuscarClienteUseCase(repo);
        var listar    = new ListarClientesUseCase(repo);
        var atualizar = new AtualizarClienteUseCase(repo);
        var remover   = new RemoverClienteUseCase(repo);

        // IDs determinísticos para os testes
        String id1 = UUID.randomUUID().toString();
        String id2 = UUID.randomUUID().toString();

        try {
            System.out.println("\n=== TESTE JPA/CLIENTE: INÍCIO ===");

            // 1) CREATE de 2 clientes
            var c1 = new Cliente(id1, "Ana", "ana@example.com");
            var c2 = new Cliente(id2, "Bruno", "bruno@example.com");
            cadastrar.executar(c1);
            cadastrar.executar(c2);
            System.out.println("[CREATE] " + c1);
            System.out.println("[CREATE] " + c2);

            // 2) READ por id
            var got1 = buscar.executar(id1).orElseThrow();
            var got2 = buscar.executar(id2).orElseThrow();
            check("read c1.id == id1", got1.getId().equals(id1));
            check("read c2.nome == Bruno", "Bruno".equals(got2.getNome()));

            // 3) LIST deve ter 2 itens
            List<Cliente> all1 = listar.executar();
            System.out.println("[LIST]   " + all1);
            check("list size == 2", all1.size() == 2);

            // 4) UPDATE cliente existente (troca nome e email)
            var c1Upd = new Cliente(id1, "Ana Souza", "ana.souza@example.com");
            atualizar.executar(c1Upd);
            var got1After = buscar.executar(id1).orElseThrow();
            check("update applied (nome)", "Ana Souza".equals(got1After.getNome()));
            check("update applied (email)", "ana.souza@example.com".equals(got1After.getEmail()));

            // 5) UPDATE cliente inexistente → deve lançar NoSuchElementException
            var fake = new Cliente(UUID.randomUUID().toString(), "X", "x@example.com");
            expectThrows("update inexistente lança NoSuchElementException", NoSuchElementException.class,
                    () -> atualizar.executar(fake));

            // 6) DELETE id2 existente
            boolean del2 = remover.executar(id2);
            check("delete existente (id2) == true", del2);

            // 7) DELETE inexistente (id2 já removido)
            boolean del2Again = remover.executar(id2);
            check("delete inexistente (id2 again) == false", !del2Again);

            // 8) LIST agora deve ter 1 (só c1)
            List<Cliente> all2 = listar.executar();
            System.out.println("[LIST]   " + all2);
            check("list size == 1", all2.size() == 1);
            check("list contém c1", all2.stream().anyMatch(c -> c.getId().equals(id1)));

            // 9) DELETE c1 e lista final vazia
            boolean del1 = remover.executar(id1);
            check("delete c1 == true", del1);

            List<Cliente> all3 = listar.executar();
            System.out.println("[LIST]   " + all3);
            check("list final vazia", all3.isEmpty());

            System.out.println("=== TESTE JPA/CLIENTE: SUCESSO ✅ ===\n");

        } finally {
            // Boa prática para fechar a fábrica ao fim do processo
            JpaUtil.close();
        }
    }
}
