package br.com.oficina.usecases.usuario;

import br.com.oficina.ports.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class ExcluirUsuarioUseCase {
    private final UsuarioRepository repo;
    public ExcluirUsuarioUseCase(UsuarioRepository repo){ this.repo = repo; }
    public boolean executar(Long id){ return repo.softDeleteById(id); }
}
