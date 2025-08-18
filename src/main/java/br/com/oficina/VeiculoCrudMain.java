package br.com.oficina;

import br.com.oficina.adapters.repositories.InMemoryVeiculoRepository;
import br.com.oficina.domain.Veiculo;
import br.com.oficina.usecases.*;

import java.util.UUID;

public class VeiculoCrudMain {
    public static void main(String[] args) {
        var repo = new InMemoryVeiculoRepository();

        var cadastrar = new CadastrarVeiculoUseCase(repo);
        var buscar    = new BuscarVeiculoUseCase(repo);
        var listar    = new ListarVeiculosUseCase(repo);
        var atualizar = new AtualizarVeiculoUseCase(repo);
        var remover   = new RemoverVeiculoUseCase(repo);

        String id = UUID.randomUUID().toString();

        // CREATE
        var v = new Veiculo(id, "ABC-1234", "Onix", "Chevrolet");
        cadastrar.executar(v);
        System.out.println("[CREATE] " + v);

        // READ
        var encontrado = buscar.executar(id).orElseThrow();
        System.out.println("[READ]   " + encontrado);

        // LIST
        System.out.println("[LIST]   " + listar.executar());

        // UPDATE (troca modelo e placa)
        var vAtualizado = new Veiculo(id, "DEF-5678", "Onix Plus", "Chevrolet");
        atualizar.executar(vAtualizado);
        System.out.println("[UPDATE] " + buscar.executar(id).orElseThrow());

        // DELETE
        boolean deletou = remover.executar(id);
        System.out.println("[DELETE] id=" + id + " -> " + deletou);

        // LIST após excluir
        System.out.println("[LIST]   " + listar.executar());
    }
}
