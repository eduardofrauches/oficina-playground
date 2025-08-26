package br.com.oficina.usecases.usuario;

import br.com.oficina.domain.Usuario;
import br.com.oficina.ports.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListarUsuariosUseCase {
    private final UsuarioRepository repo;
    public ListarUsuariosUseCase(UsuarioRepository repo){ this.repo = repo; }

    public List<Usuario> todos(){ return repo.findAll(); }
    public List<Usuario> porRole(Long roleId){ return repo.findByRoleId(roleId); }
    public List<Usuario> porNome(String trecho){ return repo.findByNomeContaining(trecho); }
}
