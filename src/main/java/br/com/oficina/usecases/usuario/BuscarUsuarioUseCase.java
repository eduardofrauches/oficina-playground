package br.com.oficina.usecases.usuario;

import br.com.oficina.domain.Usuario;
import br.com.oficina.ports.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BuscarUsuarioUseCase {
    private final UsuarioRepository repo;
    public BuscarUsuarioUseCase(UsuarioRepository repo){ this.repo = repo; }
    public Optional<Usuario> porId(Long id){ return repo.findById(id); }
}
