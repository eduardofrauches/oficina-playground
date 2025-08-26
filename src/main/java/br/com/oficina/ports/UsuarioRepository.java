package br.com.oficina.ports;

import br.com.oficina.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Usuario save(Usuario u);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    boolean softDeleteById(Long id);
    List<Usuario> findByRoleId(Long roleId);
    List<Usuario> findByNomeContaining(String trecho);
}
