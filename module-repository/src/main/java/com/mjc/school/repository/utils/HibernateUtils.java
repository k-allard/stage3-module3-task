package com.mjc.school.repository.utils;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.Tag;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Arrays;
import java.util.function.Consumer;

public final class HibernateUtils {

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    @Getter
    private static final SessionFactory sf = buildSessionFactory(Author .class, NewsModel .class, Tag .class);

    private HibernateUtils() {
    }

    public static SessionFactory buildSessionFactory(Configuration configuration, Class<?>... annotatedClasses) {
        MetadataSources metadataSources = new MetadataSources(createServiceRegistry(configuration));
        Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);

        Metadata metadata = metadataSources.getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory buildSessionFactory(String configResourceName, Class<?>... annotatedClasses) {
        Configuration configuration = new Configuration().configure(configResourceName);
        return buildSessionFactory(configuration, annotatedClasses);
    }

    public static SessionFactory buildSessionFactory(Class<?>... annotatedClasses) {
        Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);
        return buildSessionFactory(configuration, annotatedClasses);
    }

    private static StandardServiceRegistry createServiceRegistry(Configuration configuration) {
        return new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
    }

    public static void doInSession(Consumer<Session> action) {
        try (Session session = sf.openSession()) {
            action.accept(session);
        }
    }

    public static void doInSessionWithTransaction(Consumer<Session> action) {
        try (Session session = sf.openSession()) {
            Transaction t = session.getTransaction();
            t.begin();
            try {
                action.accept(session);
                t.commit();
            } catch (Exception e){
                t.rollback();
                throw e;
            }
        }
    }
}
