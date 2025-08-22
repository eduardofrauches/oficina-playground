package br.com.oficina;

import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Regras de domínio do Cliente (validações e comportamentos)")
class ClienteDomainValidationTest {

    @Test
    @DisplayName("Deve falhar ao criar Cliente sem nome")
    void semNomeDeveFalhar() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Cliente.novo(null, "a@b.com", "11999999999",
                        null, null, null, null, null)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("nome"));
    }

    @Test
    @DisplayName("Deve falhar ao criar Cliente sem email")
    void semEmailDeveFalhar() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Cliente.novo("Fulano", "  ", "11999999999",
                        null, null, null, null, null)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("email"));
    }

    @Test
    @DisplayName("Deve falhar ao criar Cliente sem telefone")
    void semTelefoneDeveFalhar() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Cliente.novo("Fulano", "a@b.com", null,
                        null, null, null, null, null)
        );
        assertTrue(ex.getMessage().toLowerCase().contains("telefone"));
    }

    @Test
    @DisplayName("Atualizar contato deve normalizar email/telefone")
    void atualizarContato() {
        var end = new Endereco("Rua A", "10", null, "Centro", "SP", "SP", "01000-000");
        var c = Cliente.novo("Fulano", "a@b.com", "1199",
                null, null, end, LocalDate.of(1999,1,1), null);

        c.atualizarContato(" novo@ex.com ", " 1198888 ");
        assertEquals("novo@ex.com", c.getEmail());
        assertEquals("1198888", c.getTelefone());
    }

    @Test
    @DisplayName("Ativar/Desativar deve alternar flag ativo")
    void ativarDesativar() {
        var c = Cliente.novo("Fulano", "a@b.com", "1199",
                null, null, null, null, null);

        assertTrue(c.isAtivo(), "Novo cliente deve iniciar ativo");
        c.desativar();
        assertFalse(c.isAtivo());
        c.ativar();
        assertTrue(c.isAtivo());
    }
}
