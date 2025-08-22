package br.com.oficina;

import br.com.oficina.adapters.repositories.InMemoryClienteRepository;
import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;
import br.com.oficina.ports.ClienteRepository;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ClienteRepository repo = new InMemoryClienteRepository();

        // Endereco é opcional; use null ou monte um VO
        Endereco end = new Endereco("Rua A", "123", null, "Centro", "São Paulo", "SP", "01000-000");

        // Use a fábrica "novo" — sem ID (o repositório atribui o Long)
        Cliente c = Cliente.novo(
                "Fulano",
                "fulano@ex.com",
                "11999999999",
                "12345678900", // cpf (opcional)
                null,          // cnpj (opcional)
                end,           // endereco (opcional)
                LocalDate.of(1990, 5, 10), // dataNascimento (opcional)
                "Cliente VIP"  // observacao (opcional)
        );

        // Persiste (em memória aqui) — o ID Long é atribuído
        c = repo.save(c);
        Long id = c.getId();
        System.out.println("Cliente criado com id = " + id);

        // Busca por ID (Long)
        repo.findById(id).ifPresent(found ->
                System.out.println("Encontrado: " + found.getNome() + " - " + found.getEmail())
        );

        // Remove por ID (Long)
        boolean removed = repo.deleteById(id);
        System.out.println("Removido? " + removed);
    }
}
