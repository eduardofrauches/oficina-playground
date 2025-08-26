package br.com.oficina.adapters.repositories;

import br.com.oficina.domain.Usuario;
import br.com.oficina.ports.UsuarioRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("dev")
public class InMemoryUsuarioRepository implements UsuarioRepository {

    private final Map<Long, Usuario> db = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    @Override
    public Usuario save(Usuario u) {
        if (u.getId() == null) u.definirId(seq.getAndIncrement());
        db.put(u.getId(), u);
        return u;
    }

    @Override
    public Optional<Usuario> findById(Long id) { return Optional.ofNullable(db.get(id)); }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return db.values().stream().filter(u -> u.getUsername().equalsIgnoreCase(username)).findFirst();
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return db.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public List<Usuario> findAll() { return new ArrayList<>(db.values()); }

    @Override
    public boolean softDeleteById(Long id) {
        Usuario u = db.get(id);
        if (u == null) return false;
        u.desativar();
        db.put(id, u);
        return true;
    }

    @Override
    public List<Usuario> findByRoleId(Long roleId) {
        return db.values().stream().filter(u -> Objects.equals(u.getRoleId(), roleId)).toList();
    }

    @Override
    public List<Usuario> findByNomeContaining(String trecho) {
        String t = (trecho == null) ? "" : trecho.toLowerCase();
        return db.values().stream().filter(u -> u.getNome().toLowerCase().contains(t)).toList();
    }
}
