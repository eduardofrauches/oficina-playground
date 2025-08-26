package br.com.oficina.usecases.usuario;

import br.com.oficina.domain.Usuario;
import br.com.oficina.ports.PasswordHasher;
import br.com.oficina.ports.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class AtualizarUsuarioUseCase {
    private final UsuarioRepository repo;
    private final PasswordHasher hasher;

    public AtualizarUsuarioUseCase(UsuarioRepository repo, PasswordHasher hasher) {
        this.repo = repo; this.hasher = hasher;
    }

    public Usuario executar(Usuario u){
        if (u.getId() == null) throw new IllegalArgumentException("id é obrigatório");

        // Se o password enviado parece estar em claro (não começa com $2),
        // aplicamos hash. Se já veio hasheado (ex: migração), apenas salva.
        String pwd = u.getPassword();
        if (pwd != null && !pwd.startsWith("$2")) {
            u.atualizarPassword(hasher.hash(pwd));
        }
        return repo.save(u);
    }
}
