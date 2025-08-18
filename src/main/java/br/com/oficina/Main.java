package br.com.oficina;

import br.com.oficina.domain.Cliente;
import br.com.oficina.adapters.repositories.InMemoryClienteRepository;
import br.com.oficina.usecases.CadastrarClienteUseCase;
import br.com.oficina.usecases.BuscarClienteUseCase;

public class Main {
    public static void main(String[] args) {
        var repository = new InMemoryClienteRepository();

        var cadastrar = new CadastrarClienteUseCase(repository);
        var buscar = new BuscarClienteUseCase(repository);

        // cria e cadastra cliente
        Cliente cliente = new Cliente("1", "Eduardo", "eduardoluiz@hotmail.com");
        cadastrar.executar(cliente);

        // busca cliente
        var resultado = buscar.executar("1");
        resultado.ifPresent(System.out::println);
    }
}
