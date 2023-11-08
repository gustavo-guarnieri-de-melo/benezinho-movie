package br.com.fiap.infra.security.repository;

import br.com.fiap.infra.security.entity.Authority;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Objects;

public class AuthorityRepository implements Repository<Authority, Long> {


    private static volatile AuthorityRepository instance;

    private final EntityManager manager;

    private AuthorityRepository(EntityManager manager) {
        this.manager = manager;
    }

    public static AuthorityRepository build(EntityManager manager) {
        AuthorityRepository result = instance;
        if (Objects.nonNull(result)) return result;
        synchronized (AuthorityRepository.class) {
            if (Objects.isNull(instance)) {
                instance = new AuthorityRepository(manager);
            }
            return instance;
        }
    }


    @Override
    public List<Authority> findAll() {
        return manager.createQuery("FROM Authority").getResultList();
    }

    @Override
    public Authority findById(Long id) {
        return manager.find(Authority.class, id);
    }

    public List<Authority> findByName(String texto) {

        String jpql = "SELECT p FROM Authority p  where lower(p.nome) LIKE CONCAT('%',lower(:nome),'%')";

        Query query = manager.createQuery(jpql);
        query.setParameter("nome", texto);
        return query.getResultList();
    }

    @Override
    public Authority persist(Authority authority) {
        manager.getTransaction().begin();
        manager.persist(authority);
        manager.getTransaction().commit();
        return authority;
    }
}
