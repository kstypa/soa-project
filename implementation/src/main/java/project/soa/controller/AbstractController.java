package project.soa.controller;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
public class AbstractController {

    protected EntityManager entityManager;

    @PostConstruct
    private void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");
        entityManager = factory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
