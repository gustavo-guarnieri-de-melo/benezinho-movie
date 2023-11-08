package br.com.fiap.infra.security.service;

import br.com.fiap.infra.database.EntityManagerFactoryProvider;
import br.com.fiap.infra.security.entity.PessoaJuridica;
import br.com.fiap.infra.security.repository.PessoaJuridicaRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Objects;

public class PessoaJuridicaService implements Service<PessoaJuridica, Long> {

    private static volatile PessoaJuridicaService instance;

    private final PessoaJuridicaRepository repo;

    private PessoaJuridicaService(PessoaJuridicaRepository repo) {
        this.repo = repo;
    }

    public static PessoaJuridicaService of(String persistenceUnit) {
        PessoaJuridicaService result = instance;
        if (Objects.nonNull(result)) return result;

        synchronized (PessoaJuridicaService.class) {
            if (Objects.isNull(instance)) {
                EntityManagerFactory factory = EntityManagerFactoryProvider.of(persistenceUnit).provide();
                PessoaJuridicaRepository pessoaRepository = PessoaJuridicaRepository.build(factory.createEntityManager());
                instance = new PessoaJuridicaService(pessoaRepository);
            }
            return instance;
        }
    }

    @Override
    public List<PessoaJuridica> findAll() {
        return repo.findAll();
    }

    @Override
    public PessoaJuridica findById(Long id) {
        return repo.findById(id);
    }

    public List<PessoaJuridica> findByName(String texto) {
        return repo.findByName(texto);
    }

    @Override
    public PessoaJuridica persist(PessoaJuridica pessoa) {
        return repo.persist(pessoa);
    }
}
