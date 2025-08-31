package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.CategoriaProduto;
import br.com.oficina.domain.Produto;
import br.com.oficina.ports.ProdutoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static br.com.oficina.adapters.repositories.jpa.ProdutoMapper.*;

@Repository
@Profile("!dev") // em dev, use in-memory
public class JpaProdutoRepository implements ProdutoRepository {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Produto save(Produto p) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ProdutoEntity e = toEntity(p);
            if (e.getId() == null) {
                em.persist(e);
            } else {
                e = em.merge(e);
            }
            tx.commit();
            return toDomain(e);
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Produto> findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            ProdutoEntity e = em.find(ProdutoEntity.class, id);
            return Optional.ofNullable(toDomain(e));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produto> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            List<ProdutoEntity> list = em.createQuery(
                    "select p from ProdutoEntity p where p.ativo = true order by p.nomeProduto", ProdutoEntity.class)
                    .getResultList();
            return list.stream().map(ProdutoMapper::toDomain).toList();
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ProdutoEntity e = em.find(ProdutoEntity.class, id);
            if (e == null) {
                tx.rollback();
                return false;
            }
            e.setAtivo(false);
            em.merge(e);
            tx.commit();
            return true;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produto> findByCategoria(CategoriaProduto categoria) {
        EntityManager em = emf.createEntityManager();
        try {
            List<ProdutoEntity> list = em.createQuery(
                    "select p from ProdutoEntity p where p.ativo = true and p.categoria = :cat order by p.nomeProduto",
                    ProdutoEntity.class)
                .setParameter("cat", categoria)
                .getResultList();
            return list.stream().map(ProdutoMapper::toDomain).toList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Produto> findByNomeContaining(String nomeLike) {
        EntityManager em = emf.createEntityManager();
        try {
            List<ProdutoEntity> list = em.createQuery(
                    "select p from ProdutoEntity p where p.ativo = true and lower(p.nomeProduto) like :q order by p.nomeProduto",
                    ProdutoEntity.class)
                .setParameter("q", "%" + (nomeLike == null ? "" : nomeLike.toLowerCase().trim()) + "%")
                .getResultList();
            return list.stream().map(ProdutoMapper::toDomain).toList();
        } finally {
            em.close();
        }
    }
}
