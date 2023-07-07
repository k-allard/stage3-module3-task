package com.mjc.school.repository.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

@Component
public class JPAUtils {

    @Getter
    @Autowired
    private EntityManagerFactory emf;

     public JPAUtils(EntityManagerFactory entityManagerFactory) {
         emf = entityManagerFactory;
     }

    public void doInSessionWithTransaction(Consumer<EntityManager> action) {
        EntityManager session = emf.createEntityManager();
        EntityTransaction t = session.getTransaction();
        t.begin();
        try {
            action.accept(session);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            throw e;
        }
    }
}
