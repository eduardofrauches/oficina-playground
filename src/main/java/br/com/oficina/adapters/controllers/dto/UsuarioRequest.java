package br.com.oficina.adapters.controllers.dto;

import br.com.oficina.domain.Usuario;

public record UsuarioRequest(
        String username,
        String nome,
        String email,
        String password,
        Long roleId
){
    public Usuario toDomainNovo(){
        return Usuario.novo(username, nome, email, password, roleId);
    }

    // Mantém senha atual se não for enviada; mantém 'ativo' do usuário atual
    public Usuario toDomainAtualizacao(Long id, Usuario atual){
        String pwd = (this.password == null) ? atual.getPassword() : this.password;
        boolean ativoAtual = atual.isAtivo();
        return new Usuario(id, username, nome, email, pwd, ativoAtual, roleId);
    }
}
