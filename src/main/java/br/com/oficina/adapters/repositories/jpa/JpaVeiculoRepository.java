package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Veiculo;
import br.com.oficina.ports.VeiculoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("!dev") // em dev, use InMemoryVeiculoRepository
public class JpaVeiculoRepository implements VeiculoRepository {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Veiculo save(Veiculo v) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            VeiculoEntity e = toEntity(v);
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
    public Optional<Veiculo> findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            VeiculoEntity e = em.find(VeiculoEntity.class, id);
            return Optional.ofNullable(e).map(this::toDomain);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Veiculo> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            List<VeiculoEntity> list = em.createQuery(
                    "select v from VeiculoEntity v", VeiculoEntity.class
            ).getResultList();
            return list.stream().map(this::toDomain).toList();
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
            VeiculoEntity e = em.find(VeiculoEntity.class, id);
            if (e != null) {
                em.remove(e);
                tx.commit();
                return true;
            } else {
                tx.commit();
                return false;
            }
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // ====== métodos que a PORTA expõe ======

    // Porta pede: Optional<Veiculo> findByPlaca(String placa)
    @Override
    public Optional<Veiculo> findByPlaca(String placa) {
        if (placa == null) return Optional.empty();
        String p = placa.trim().toLowerCase();
        EntityManager em = emf.createEntityManager();
        try {
            VeiculoEntity e = em.createQuery(
                    "select v from VeiculoEntity v where lower(v.placa) = :placa",
                    VeiculoEntity.class
            ).setParameter("placa", p)
             .getResultStream()
             .findFirst()
             .orElse(null);
            return Optional.ofNullable(e).map(this::toDomain);
        } finally {
            em.close();
        }
    }

    // Porta pede: List<Veiculo> findByClienteId(Long clienteId)
    @Override
    public List<Veiculo> findByClienteId(Long clienteId) {
        EntityManager em = emf.createEntityManager();
        try {
            List<VeiculoEntity> list = em.createQuery(
                    "select v from VeiculoEntity v where v.clienteId = :cid",
                    VeiculoEntity.class
            ).setParameter("cid", clienteId)
             .getResultList();

            return list.stream().map(this::toDomain).toList();
        } finally {
            em.close();
        }
    }

    // ====== helpers opcionais (NÃO fazem parte da porta) ======

    public List<Veiculo> findByPlacaContainingIgnoreCaseHelper(String termo) {
        if (termo == null || termo.trim().isEmpty()) return List.of();
        String t = "%" + termo.trim().toLowerCase() + "%";
        EntityManager em = emf.createEntityManager();
        try {
            List<VeiculoEntity> list = em.createQuery(
                    "select v from VeiculoEntity v where lower(v.placa) like :termo",
                    VeiculoEntity.class
            ).setParameter("termo", t)
             .getResultList();
            return list.stream().map(this::toDomain).toList();
        } finally {
            em.close();
        }
    }

    public boolean existsByPlacaIgnoreCaseHelper(String placa) {
        if (placa == null) return false;
        String p = placa.trim().toLowerCase();
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                    "select count(v) from VeiculoEntity v where lower(v.placa) = :placa",
                    Long.class
            ).setParameter("placa", p)
             .getSingleResult();
            return count != null && count > 0;
        } finally {
            em.close();
        }
    }

    // ====== mapeamento ======

    private Veiculo toDomain(VeiculoEntity e) {
        if (e == null) return null;
        boolean ativo = (e.getAtivo() == null) || e.getAtivo();
        return new Veiculo(
                e.getId(),
                e.getPlaca(),
                e.getMarca(),
                e.getModelo(),
                e.getAno(),
                e.getCor(),
                e.getObservacoes(),
                e.getClienteId(),
                ativo
        );
    }

    private VeiculoEntity toEntity(Veiculo v) {
        VeiculoEntity e = new VeiculoEntity();
        e.setId(v.getId());
        e.setPlaca(v.getPlaca());
        e.setMarca(v.getMarca());
        e.setModelo(v.getModelo());
        e.setAno(v.getAno());
        e.setCor(v.getCor());
        e.setObservacoes(v.getObservacoes());
        e.setClienteId(v.getClienteId());
        e.setAtivo(v.isAtivo());
        return e;
    }
}
