package br.com.fiap.infra.database;


import br.com.fiap.Main;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.glassfish.hk2.api.Factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityManagerFactoryProvider implements Factory<EntityManagerFactory> {

    private static volatile EntityManagerFactoryProvider instance;

    private final EntityManagerFactory emf;

    public static EntityManagerFactoryProvider of(String persistentUnit) {
        EntityManagerFactoryProvider result = instance;
        if (Objects.nonNull( result )) return result;

        synchronized (EntityManagerFactoryProvider.class) {
            if (Objects.isNull( instance )) {
                instance = new EntityManagerFactoryProvider( persistentUnit );
            }
            return instance;
        }
    }

    private EntityManagerFactoryProvider(String persistentUnit) {
        emf = Persistence.createEntityManagerFactory( persistentUnit, getProperties() );
    }

    @Override
    public EntityManagerFactory provide() {
        return emf;
    }

    @Override
    public void dispose(EntityManagerFactory instance) {
        instance.close();
    }

    /**
     * Protegendo informações críticas como usuário e senha do banco de dados
     *
     * @return
     */
    static Map<String, Object> getProperties() {

        Map<String, String> env = System.getenv();

        Map<String, Object> properties = new HashMap<>();

        for (String chave : env.keySet()) {

            if (Main.PERSISTENCE_UNIT.equals( "oracle-fiap" )) {

                if (chave.contains( "USER_FIAP" )) {
                    properties.put( "jakarta.persistence.jdbc.user", env.get( chave ) );
                }
                if (chave.contains( "PASSWORD_FIAP" )) {
                    properties.put( "jakarta.persistence.jdbc.password", env.get( chave ) );
                }

            }

            // Outras configurações de propriedade ....
        }
        return properties;
    }

} 