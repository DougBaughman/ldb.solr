package ldb.solr.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtility {
    static private final Logger logger = LoggerFactory.getLogger(EntityManagerUtility.class);

    private static final EntityManagerFactory emFactory;


    static {
        emFactory = Persistence.createEntityManagerFactory( "ldb.solr" );
    }


    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

}
