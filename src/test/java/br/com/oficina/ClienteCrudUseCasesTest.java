package br.com.oficina;

import br.com.oficina.usecases.cliente.CadastrarClienteUseCase;
import br.com.oficina.usecases.cliente.BuscarClienteUseCase;
import br.com.oficina.usecases.cliente.ListarClientesUseCase;
import br.com.oficina.usecases.cliente.AtualizarClienteUseCase;
import br.com.oficina.usecases.cliente.RemoverClienteUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Variante alternativa quando o projeto usa os nomes
 * Cadastrar/Remover em vez de Criar/Excluir.
 */
class ClienteCrudUseCasesTest {

    @Test
    void deveInstanciarUseCases() {
        CadastrarClienteUseCase cadastrar = new CadastrarClienteUseCase(null);
        BuscarClienteUseCase buscar = new BuscarClienteUseCase(null);
        ListarClientesUseCase listar = new ListarClientesUseCase(null);
        AtualizarClienteUseCase atualizar = new AtualizarClienteUseCase(null);
        RemoverClienteUseCase remover = new RemoverClienteUseCase(null);

        assertNotNull(cadastrar);
        assertNotNull(buscar);
        assertNotNull(listar);
        assertNotNull(atualizar);
        assertNotNull(remover);
    }
}
