package br.com.oficina;

import br.com.oficina.adapters.repositories.jpa.JpaClienteRepository;
import br.com.oficina.adapters.repositories.jpa.JpaUtil;
import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;
import br.com.oficina.usecases.*;

import java.time.LocalDate;
import java.util.List;

public class JpaClienteMain {

    // Helper simples de “assert”
    static void check(String name, boolean condition) {
        if (!condition) throw new AssertionError("Falhou: " + name);
        System.out.println("OK: " + name);
    }

    static <T> T must(T v, String msg) {
        if (v == null) throw new AssertionError(msg);
        return v;
    }

    public static void main(String[] args) {
        var repository = new JpaClienteRepository();

        var cadastrar = new CadastrarClienteUseCase(repository);
        var buscar    = new BuscarClienteUseCase(repository);
        var listar    = new ListarClientesUseCase(repository);
        var atualizar = new AtualizarClienteUseCase(repository);
        var remover   = new RemoverClienteUseCase(repository);

        try {
            System.out.println("=== TESTE JPA/CLIENTE ===");

            var end = new Endereco("Rua A", "123", null, "Centro", "São Paulo", "SP", "01000-000");

            // CREATE
            Cliente c1 = Cliente.novo(
                    "João",
                    "joao@example.com",
                    "11988887777",
                    "98765432100",
                    null,
                    end,
                    LocalDate.of(1985, 5, 10),
                    "Primeiro cadastro"
            );
            c1 = cadastrar.executar(c1);
            Long id1 = must(c1.getId(), "Esperava id não-nulo");
            System.out.println("[CREATE] id=" + id1);

            // CREATE 2
            Cliente c2 = Cliente.novo(
                    "Maria",
                    "maria@example.com",
                    "11888887777",
                    null,
                    "12.345.678/0001-99",
                    null,
                    null,
                    null
            );
            c2 = cadastrar.executar(c2);
            Long id2 = must(c2.getId(), "Esperava id não-nulo");
            System.out.println("[CREATE] id=" + id2);

            // READ
            Cliente joao = buscar.executar(id1).orElseThrow();
            System.out.println("[READ]   " + joao);

            // LIST
            List<Cliente> all = listar.executar();
            System.out.println("[LIST]   " + all);
            check("list size >= 2", all.size() >= 2);

            // UPDATE João
            Cliente joaoAtual = Cliente.novo(
                    "João Silva",
                    "joao.silva@example.com",
                    "11988887777",
                    "98765432100",
                    null,
                    end,
                    LocalDate.of(1985, 5, 10),
                    "Atualizado"
            );
            joaoAtual.definirId(id1);
            atualizar.executar(joaoAtual);
            Cliente joaoR = buscar.executar(id1).orElseThrow();
            System.out.println("[UPDATE] " + joaoR);
            check("nome atualizado", "João Silva".equals(joaoR.getNome()));

            // DELETE Maria
            boolean del2 = remover.executar(id2);
            check("delete c2 == true", del2);

            // DELETE João
            boolean del1 = remover.executar(id1);
            check("delete c1 == true", del1);

            List<Cliente> all3 = listar.executar();
            System.out.println("[LIST]   " + all3);
            check("list final vazia", all3.isEmpty());

            System.out.println("=== TESTE JPA/CLIENTE: SUCESSO ✅ ===\n");

        } finally {
            // Fecha a factory do JPA
            JpaUtil.close();
        }
    }
}
