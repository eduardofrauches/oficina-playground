package br.com.oficina;

import br.com.oficina.adapters.repositories.InMemoryClienteRepository;
import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;
import br.com.oficina.usecases.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CRUD de Cliente via UseCases com repositório em memória")
class ClienteCrudUseCasesTest {

    private CadastrarClienteUseCase cadastrar;
    private BuscarClienteUseCase buscar;
    private ListarClientesUseCase listar;
    private AtualizarClienteUseCase atualizar;
    private RemoverClienteUseCase remover;

    @BeforeEach
    void setup() {
        var repo = new InMemoryClienteRepository();
        cadastrar = new CadastrarClienteUseCase(repo);
        buscar    = new BuscarClienteUseCase(repo);
        listar    = new ListarClientesUseCase(repo);
        atualizar = new AtualizarClienteUseCase(repo);
        remover   = new RemoverClienteUseCase(repo);
    }

    @Test
    @DisplayName("Deve realizar o fluxo completo: create → read → list → update → delete")
    void fluxoCrudCompleto() {
        var end = new Endereco("Rua A", "123", null, "Centro", "São Paulo", "SP", "01000-000");

        // CREATE
        Cliente c = Cliente.novo(
                "Fulano", "fulano@ex.com", "11999999999",
                "12345678900", null, end,
                LocalDate.of(1990, 5, 10), "Observação"
        );
        c = cadastrar.executar(c);
        assertNotNull(c.getId(), "ID deve ser atribuído pelo repositório");

        Long id = c.getId();

        // READ
        Cliente found = buscar.executar(id).orElseThrow();
        assertEquals("Fulano", found.getNome());
        assertEquals("fulano@ex.com", found.getEmail());

        // LIST
        var lista = listar.executar();
        assertFalse(lista.isEmpty());
        assertTrue(lista.stream().anyMatch(cl -> cl.getId().equals(id)));

        // UPDATE (trocar nome/email)
        Cliente novo = Cliente.novo(
                "Fulano da Silva", "fulano.silva@ex.com", "11999999999",
                "12345678900", null, end,
                LocalDate.of(1990, 5, 10), "VIP"
        );
        novo.definirId(id);
        var atualizado = atualizar.executar(novo);

        assertEquals(id, atualizado.getId());
        assertEquals("Fulano da Silva", atualizado.getNome());
        assertEquals("fulano.silva@ex.com", atualizado.getEmail());

        // DELETE
        boolean removed = remover.executar(id);
        assertTrue(removed);

        // LIST final
        var listaFinal = listar.executar();
        assertTrue(listaFinal.isEmpty(), "Após remover único cliente, lista deve ficar vazia");
    }
}
