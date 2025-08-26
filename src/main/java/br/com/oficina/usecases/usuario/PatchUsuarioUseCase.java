package br.com.oficina.usecases.usuario;

import br.com.oficina.domain.Usuario;
import br.com.oficina.ports.PasswordHasher;
import br.com.oficina.ports.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class PatchUsuarioUseCase {

    private final UsuarioRepository repo;
    private final PasswordHasher hasher;

    public PatchUsuarioUseCase(UsuarioRepository repo, PasswordHasher hasher) {
        this.repo = repo;
        this.hasher = hasher;
    }

    public Usuario atualizarNome(Long id, String nome){
        Usuario u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
        u.atualizarNome(nome);
        return repo.save(u);
    }

    public Usuario atualizarEmail(Long id, String email){
        Usuario u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
        u.atualizarEmail(email);
        return repo.save(u);
    }

    public Usuario atualizarUsername(Long id, String username){
        Usuario u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
        u.atualizarUsername(username);
        return repo.save(u);
    }

    /**
     * Sempre aplica hash ao atualizar a senha.
     * Espera receber a senha em texto claro; o método cuida de gerar o hash.
     */
    public Usuario atualizarPassword(Long id, String password){
        if (password == null) throw new IllegalArgumentException("password inválido");
        Usuario u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
        String hashed = hasher.hash(password);
        u.atualizarPassword(hashed);
        return repo.save(u);
    }

    public Usuario atualizarRole(Long id, Long roleId){
        Usuario u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
        u.atualizarRole(roleId);
        return repo.save(u);
    }

    public Usuario desativar(Long id){
        Usuario u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
        u.desativar();
        return repo.save(u);
    }

    public Usuario reativar(Long id){
        Usuario u = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("usuário não encontrado"));
        u.reativar();
        return repo.save(u);
    }
}
