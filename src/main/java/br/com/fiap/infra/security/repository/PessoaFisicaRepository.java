package br.com.fiap.infra.security.repository;

import br.com.fiap.infra.security.entity.Authority;
import br.com.fiap.infra.security.entity.PessoaFisica;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Objects;

public class PessoaFisicaRepository implements Repository<PessoaFisica, Long> {

    private static volatile PessoaFisicaRepository instance;

    private final AuthorityRepository authorityRepository;

    private final EntityManager manager;

    private PessoaFisicaRepository(EntityManager manager) {
        this.manager = manager;
        this.authorityRepository = AuthorityRepository.build( manager );
    }

    public static PessoaFisicaRepository build(EntityManager manager) {
        PessoaFisicaRepository result = instance;
        if (Objects.nonNull( result )) return result;

        synchronized (PessoaFisicaRepository.class) {
            if (Objects.isNull( instance )) {
                instance = new PessoaFisicaRepository( manager );
            }
            return instance;
        }
    }


    @Override
    public List<PessoaFisica> findAll() {
        List<PessoaFisica> list = manager.createQuery( "FROM PessoaFisica" ).getResultList();
        return list;
    }

    @Override
    public PessoaFisica findById(Long id) {
        PessoaFisica pessoa = manager.find( PessoaFisica.class, id );
        return pessoa;
    }

    public List<PessoaFisica> findByName(String texto) {
        String jpql = "SELECT p FROM PessoaFisica p  where lower(p.nome) LIKE CONCAT('%',lower(:nome),'%')";
        Query query = manager.createQuery( jpql );
        query.setParameter( "nome", texto );
        List<PessoaFisica> list = query.getResultList();
        return list;
    }

    @Override
    public PessoaFisica persist(PessoaFisica pessoa) {
        manager.getTransaction().begin();
        List<Authority> authorities = authorityRepository.findByName( "cliente" );
        if (authorities.size() == 0) {
            Authority cli = new Authority();
            cli.setNome( "cliente" );
            authorities.add( cli );
        }
        authorities.forEach( pessoa.getUsuario()::addAuthority );
        pessoa = manager.merge( pessoa );
        manager.getTransaction().commit();
        return pessoa;
    }
}
