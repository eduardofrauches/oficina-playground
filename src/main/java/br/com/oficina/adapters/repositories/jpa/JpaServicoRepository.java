package br.com.oficina.adapters.repositories.jpa;

import br.com.oficina.domain.Servico;
import br.com.oficina.ports.ServicoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static br.com.oficina.adapters.repositories.jpa.ServicoMapper.*;

@Repository
public class JpaServicoRepository implements ServicoRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Servico salvar(Servico servico) {
        ServicoEntity entity = toEntity(servico);
        if (entity.getId() == null) em.persist(entity);
        else entity = em.merge(entity);
        em.flush();
        return toDomain(entity);
    }

    @Override
    public Optional<Servico> buscarPorId(Long id) {
        ServicoEntity e = em.find(ServicoEntity.class, id);
        return Optional.ofNullable(e).map(ServicoMapper::toDomain);
    }

    @Override
    public List<Servico> listarTodos() {
        TypedQuery<ServicoEntity> q = em.createQuery(
                "select s from ServicoEntity s where s.ativo = true order by s.nome asc",
                ServicoEntity.class);
        return q.getResultList().stream().map(ServicoMapper::toDomain).toList();
    }

    @Override
    public List<Servico> buscarPorPalavras(String termos) {
        if (termos == null || termos.isBlank()) return listarTodos();
        String[] tokens = termos.toLowerCase(Locale.ROOT).trim().split("\s+");
        StringBuilder jpql = new StringBuilder(
                "select s from ServicoEntity s where s.ativo = true and (" +
                "lower(coalesce(s.nome,'')) || ' ' || lower(coalesce(s.descricao,'')) like :t0");
        for (int i = 1; i < tokens.length; i++) {
            jpql.append(" and lower(coalesce(s.nome,'')) || ' ' || lower(coalesce(s.descricao,'')) like :t").append(i);
        }
        jpql.append(") order by s.nome asc");
        TypedQuery<ServicoEntity> q = em.createQuery(jpql.toString(), ServicoEntity.class);
        for (int i = 0; i < tokens.length; i++) q.setParameter("t"+i, "%"+tokens[i]+"%");
        return q.getResultList().stream().map(ServicoMapper::toDomain).toList();
    }
}
