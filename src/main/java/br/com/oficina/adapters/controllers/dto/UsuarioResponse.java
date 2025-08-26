package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.Usuario;

public record UsuarioResponse(
        Long id,
        String username,
        String nome,
        String email,
        boolean ativo,
        Long roleId
){
    public static UsuarioResponse from(Usuario u){
        return new UsuarioResponse(u.getId(), u.getUsername(), u.getNome(), u.getEmail(), u.isAtivo(), u.getRoleId());
    }
}
