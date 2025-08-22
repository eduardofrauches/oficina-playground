package br.com.oficina;

import br.com.oficina.adapters.repositories.jpa.JpaClienteRepository;
import br.com.oficina.adapters.repositories.jpa.JpaUtil;
import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IT: JpaClienteRepository com H2 em memória")
class JpaClienteRepositoryTest {

    static JpaClienteRepository repo;

    @BeforeAll
    static void init() {
        repo = new JpaClienteRepository();
    }

    @AfterAll
    static void teardown() {
        // fecha a factory criada por JpaUtil
        JpaUtil.close();
    }

    @Test
    @DisplayName("Deve fazer CRUD completo (save/findAll/findById/deleteById)")
    void crudCompleto() {
        var end = new Endereco("Rua A", "123", null, "Centro", "São Paulo", "SP", "01000-000");

        // CREATE
        var novo = Cliente.novo(
                "João", "joao@example.com", "11988887777",
                "98765432100", null, end,
                LocalDate.of(1985, 5, 10), "Integração"
        );
        var salvo = repo.save(novo);
        assertNotNull(salvo.getId(), "ID deve ser gerado");
        var id = salvo.getId();

        // READ
        var encontrado = repo.findById(id).orElseThrow();
        assertEquals("João", encontrado.getNome());

        // LIST
        var todos = repo.findAll();
        assertFalse(todos.isEmpty());
        assertTrue(todos.stream().anyMatch(c -> id.equals(c.getId())));

        // UPDATE (mudar nome e email)
        var att = Cliente.novo(
                "João Silva", "joao.silva@example.com", "11988887777",
                "98765432100", null, end,
                LocalDate.of(1985, 5, 10), "Atualizado"
        );
        att.definirId(id);
        var atualizado = repo.save(att);
        assertEquals("João Silva", atualizado.getNome());
        assertEquals("joao.silva@example.com", atualizado.getEmail());

        // DELETE
        assertTrue(repo.deleteById(id));
        assertTrue(repo.findAll().isEmpty(), "Após remover único registro, lista deve ficar vazia");
    }
}
