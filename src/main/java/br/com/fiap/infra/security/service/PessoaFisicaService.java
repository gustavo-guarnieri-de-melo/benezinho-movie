package br.com.fiap.infra.security.service;

import br.com.fiap.infra.database.EntityManagerFactoryProvider;
import br.com.fiap.infra.security.entity.PessoaFisica;
import br.com.fiap.infra.security.repository.PessoaFisicaRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Objects;

public class PessoaFisicaService implements Service<PessoaFisica, Long> {

    private static volatile PessoaFisicaService instance;

    private final PessoaFisicaRepository repo;


    private PessoaFisicaService(PessoaFisicaRepository repo) {
        this.repo = repo;

    }

    public static PessoaFisicaService of(String persistenceUnit) {
        PessoaFisicaService result = instance;
        if (Objects.nonNull(result)) return result;

        synchronized (PessoaFisicaService.class) {
            if (Objects.isNull(instance)) {

                EntityManagerFactory factory = EntityManagerFactoryProvider.of(persistenceUnit).provide();

                PessoaFisicaRepository pessoaRepository = PessoaFisicaRepository.build(factory.createEntityManager());


                instance = new PessoaFisicaService(pessoaRepository);
            }
            return instance;
        }
    }

    @Override
    public List<PessoaFisica> findAll() {
        return repo.findAll();
    }

    @Override
    public PessoaFisica findById(Long id) {
        return repo.findById(id);
    }

    public List<PessoaFisica> findByName(String texto) {
        return repo.findByName(texto);
    }

    @Override
    public PessoaFisica persist(PessoaFisica pessoa) {
        if (Objects.isNull(pessoa)) return null;
        PessoaFisica persisted = repo.persist(pessoa);
        return persisted;
    }
}
