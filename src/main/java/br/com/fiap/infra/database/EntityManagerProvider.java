package br.com.fiap.infra.database;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.CloseableService;

import java.io.Closeable;
import java.io.IOException;


public class EntityManagerProvider implements Factory<EntityManager> {

    private EntityManagerFactory emf;
    private CloseableService closeableService;

    @Inject
    public EntityManagerProvider(EntityManagerFactory emf, CloseableService closeableService) {
        this.emf = emf;
        this.closeableService = closeableService;
    }

    @Inject
    public void EMFactory(EntityManagerFactory emf, CloseableService closeService) {
        this.emf = emf;
        this.closeableService = closeService;
    }

    public EntityManager provide() {
        final EntityManager em = emf.createEntityManager();
        this.closeableService.add( new Closeable() {
            @Override
            public void close() throws IOException {
                if (emf.isOpen()) {
                    emf.close();
                }
            }
        } );
        return em;
    }

    @Override
    public void dispose(EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

}