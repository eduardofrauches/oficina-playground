package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Cliente;
import br.com.oficina.domain.vo.Endereco;
import br.com.oficina.ports.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório JPA usando o EntityManagerFactory gerenciado pelo Spring Boot.
 * - Sem JpaUtil/persistence.xml.
 * - O datasource/dialect vem do application-*.yaml do profile ativo.
 */
@Repository
@Profile("!dev") // em dev, use o InMemoryClienteRepository
public class JpaClienteRepository implements ClienteRepository {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public Cliente save(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            ClienteEntity entity = toEntity(cliente);

            if (entity.getId() == null) {
                em.persist(entity);
            } else {
                entity = em.merge(entity);
            }

            tx.commit();
            return toDomain(entity);

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Cliente> findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            ClienteEntity entity = em.find(ClienteEntity.class, id);
            return Optional.ofNullable(entity).map(this::toDomain);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            List<ClienteEntity> list = em.createQuery(
                    "select c from ClienteEntity c", ClienteEntity.class
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
            ClienteEntity entity = em.find(ClienteEntity.class, id);
            if (entity != null) {
                em.remove(entity);
                tx.commit();
                return true;
            } else {
                tx.commit();
                return false;
            }
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // =======================
    // MAPEAMENTO inline
    // =======================

    private Cliente toDomain(ClienteEntity e) {
        if (e == null) return null;

        Endereco endereco = null;
        EnderecoEmbeddable emb = e.getEndereco();
        if (emb != null) {
            endereco = new Endereco(
                    emb.getLogradouro(),
                    emb.getNumero(),
                    emb.getComplemento(),
                    emb.getBairro(),
                    emb.getCidade(),
                    emb.getEstado(),
                    emb.getCep()
            );
        }

        Boolean ativoEntity = e.getAtivo();
        boolean ativo = (ativoEntity == null) ? true : ativoEntity;

        return new Cliente(
                e.getId(),
                e.getNome(),
                e.getCpf(),
                e.getCnpj(),
                e.getEmail(),
                e.getTelefone(),
                endereco,
                e.getDataCadastro(),
                e.getDataNascimento(),
                e.getObservacao(),
                ativo
        );
    }

    private ClienteEntity toEntity(Cliente c) {
        ClienteEntity e = new ClienteEntity();

        e.setId(c.getId());
        e.setNome(c.getNome());
        e.setCpf(c.getCpf());
        e.setCnpj(c.getCnpj());
        e.setEmail(c.getEmail());
        e.setTelefone(c.getTelefone());

        if (c.getEndereco() != null) {
            Endereco vo = c.getEndereco();
            EnderecoEmbeddable emb = new EnderecoEmbeddable();
            emb.setLogradouro(vo.getLogradouro());
            emb.setNumero(vo.getNumero());
            emb.setComplemento(vo.getComplemento());
            emb.setBairro(vo.getBairro());
            emb.setCidade(vo.getCidade());
            emb.setEstado(vo.getEstado());
            emb.setCep(vo.getCep());
            e.setEndereco(emb);
        } else {
            e.setEndereco(null);
        }

        e.setDataCadastro(c.getDataCadastro());
        e.setDataNascimento(c.getDataNascimento());
        e.setObservacao(c.getObservacao());
        e.setAtivo(c.isAtivo()); // domínio expõe isAtivo()

        return e;
    }
}
