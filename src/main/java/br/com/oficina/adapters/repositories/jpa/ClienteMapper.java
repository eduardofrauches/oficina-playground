package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Cliente;

public final class ClienteMapper {
    private ClienteMapper(){}

    public static ClienteEntity toEntity(Cliente c) {
        return new ClienteEntity(c.getId(), c.getNome(), c.getEmail());
    }

    public static Cliente toDomain(ClienteEntity e) {
        return new Cliente(e.getId(), e.getNome(), e.getEmail());
    }
}
