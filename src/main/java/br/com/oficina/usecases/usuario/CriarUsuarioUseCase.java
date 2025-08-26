package br.com.oficina.usecases.usuario;

import br.com.oficina.domain.Usuario;
import br.com.oficina.ports.PasswordHasher;
import br.com.oficina.ports.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class CriarUsuarioUseCase {
    private final UsuarioRepository repo;
    private final PasswordHasher hasher;

    public CriarUsuarioUseCase(UsuarioRepository repo, PasswordHasher hasher) {
        this.repo = repo;
        this.hasher = hasher;
    }

    public Usuario executar(Usuario usuario) {
        // Hashear SEMPRE ao criar
        String hashed = hasher.hash(usuario.getPassword());
        usuario.atualizarPassword(hashed); // guarda hash no domínio antes de salvar
        return repo.save(usuario);
    }
}
