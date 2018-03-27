package ldb.solr.crud;

import ldb.solr.entity.Person;
import ldb.solr.jpa.EntityManagerUtility;

import javax.persistence.EntityManager;

public class PersonCrud {

    public Person create( Person person){
        EntityManager entityManager = EntityManagerUtility.getEntityManager();
        entityManager.getTransaction().begin();
        person.setId(null);
        entityManager.persist(person);
        entityManager.getTransaction().commit();
        entityManager.close();
        return person;
    }

    public Person update( Long id, Person person){
        EntityManager entityManager = EntityManagerUtility.getEntityManager();
        entityManager.getTransaction().begin();
        person.setId(id);
        person = entityManager.merge(person);
        entityManager.getTransaction().commit();
        entityManager.close();
        return person;
    }

    public void delete( Long id){
        EntityManager entityManager = EntityManagerUtility.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Person.class, id));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Person fetch( Long id){
        EntityManager entityManager = EntityManagerUtility.getEntityManager();
        entityManager.getTransaction().begin();
        Person person = entityManager.find(Person.class, id);
        entityManager.close();
        return person;
    }

}
