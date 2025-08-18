package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Cliente;
import br.com.oficina.ports.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static br.com.oficina.adapters.repositories.jpa.ClienteMapper.*;

public class JpaClienteRepository implements ClienteRepository {

    @Override
    public Cliente save(Cliente cliente) {
        EntityManager em = JpaUtil.emf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(toEntity(cliente));
            tx.commit();
            return cliente;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Cliente> findById(String id) {
        EntityManager em = JpaUtil.emf().createEntityManager();
        try {
            ClienteEntity e = em.find(ClienteEntity.class, id);
            return e == null ? Optional.empty() : Optional.of(toDomain(e));
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> findAll() {
        EntityManager em = JpaUtil.emf().createEntityManager();
        try {
            var list = em.createQuery("select c from ClienteEntity c", ClienteEntity.class).getResultList();
            var out = new ArrayList<Cliente>(list.size());
            for (ClienteEntity e : list) out.add(toDomain(e));
            return out;
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente update(Cliente cliente) {
        EntityManager em = JpaUtil.emf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ClienteEntity e = em.find(ClienteEntity.class, cliente.getId());
            if (e == null) throw new NoSuchElementException("Cliente não encontrado: " + cliente.getId());
            e.setNome(cliente.getNome());
            e.setEmail(cliente.getEmail());
            em.merge(e);
            tx.commit();
            return cliente;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(String id) {
        EntityManager em = JpaUtil.emf().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            ClienteEntity e = em.find(ClienteEntity.class, id);
            if (e == null) {
                tx.commit();
                return false;
            }
            em.remove(e);
            tx.commit();
            return true;
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
