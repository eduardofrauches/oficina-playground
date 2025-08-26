package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Usuario;
import br.com.oficina.ports.UsuarioRepository;
import jakarta.persistence.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("!dev")
public class JpaUsuarioRepository implements UsuarioRepository {

    @PersistenceUnit
    private EntityManagerFactory emf;

    private Usuario toDomain(UsuarioEntity e) {
        return new Usuario(e.getId(), e.getUsername(), e.getNome(), e.getEmail(), e.getPassword(), e.isAtivo(), e.getRoleId());
    }
    private UsuarioEntity toEntity(Usuario d) {
        UsuarioEntity e = new UsuarioEntity();
        e.setId(d.getId());
        e.setUsername(d.getUsername());
        e.setNome(d.getNome());
        e.setEmail(d.getEmail());
        e.setPassword(d.getPassword());
        e.setAtivo(d.isAtivo());
        e.setRoleId(d.getRoleId());
        return e;
    }

    @Override
    public Usuario save(Usuario u) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            UsuarioEntity e = toEntity(u);
            if (e.getId() == null) em.persist(e); else e = em.merge(e);
            tx.commit();
            u.definirId(e.getId());
            return u;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally { em.close(); }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            UsuarioEntity e = em.find(UsuarioEntity.class, id);
            return Optional.ofNullable(e).map(this::toDomain);
        } finally { em.close(); }
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        EntityManager em = emf.createEntityManager();
        try {
            List<UsuarioEntity> list = em.createQuery("select u from UsuarioEntity u where lower(u.username)=:x", UsuarioEntity.class)
                    .setParameter("x", username.toLowerCase()).getResultList();
            return list.stream().findFirst().map(this::toDomain);
        } finally { em.close(); }
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            List<UsuarioEntity> list = em.createQuery("select u from UsuarioEntity u where lower(u.email)=:x", UsuarioEntity.class)
                    .setParameter("x", email.toLowerCase()).getResultList();
            return list.stream().findFirst().map(this::toDomain);
        } finally { em.close(); }
    }

    @Override
    public List<Usuario> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("select u from UsuarioEntity u order by u.id desc", UsuarioEntity.class)
                    .getResultList().stream().map(this::toDomain).toList();
        } finally { em.close(); }
    }

    @Override
    public boolean softDeleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            UsuarioEntity e = em.find(UsuarioEntity.class, id);
            if (e == null) { tx.commit(); return false; }
            e.setAtivo(false);
            em.merge(e);
            tx.commit();
            return true;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally { em.close(); }
    }

    @Override
    public List<Usuario> findByRoleId(Long roleId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("select u from UsuarioEntity u where u.roleId=:r", UsuarioEntity.class)
                    .setParameter("r", roleId).getResultList().stream().map(this::toDomain).toList();
        } finally { em.close(); }
    }

    @Override
    public List<Usuario> findByNomeContaining(String trecho) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("select u from UsuarioEntity u where lower(u.nome) like :n", UsuarioEntity.class)
                    .setParameter("n", "%" + (trecho == null ? "" : trecho.toLowerCase()) + "%")
                    .getResultList().stream().map(this::toDomain).toList();
        } finally { em.close(); }
    }
}
