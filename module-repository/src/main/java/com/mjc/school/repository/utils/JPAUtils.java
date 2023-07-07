package com.mjc.school.repository.utils;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import lombok.Getter;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.function.Consumer;

public final class JPAUtils {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    @Getter
    private static final EntityManagerFactory sf = buildSessionFactory(Author.class, News.class, Tag.class);

    private JPAUtils() {
    }

    public static EntityManagerFactory buildSessionFactory(Configuration configuration, Class<?>... annotatedClasses) {
        MetadataSources metadataSources = new MetadataSources(createServiceRegistry(configuration));
        Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);

        Metadata metadata = metadataSources.getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }

    public static EntityManagerFactory buildSessionFactory(Class<?>... annotatedClasses) {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        return buildSessionFactory(configuration, annotatedClasses);
    }

    private static StandardServiceRegistry createServiceRegistry(Configuration configuration) {
        return new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
    }

    public static void doInSessionWithTransaction(Consumer<EntityManager> action) {
        EntityManager session = sf.createEntityManager();
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
