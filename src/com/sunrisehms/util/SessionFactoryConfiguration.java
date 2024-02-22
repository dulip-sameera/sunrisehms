
package com.sunrisehms.util;

import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.entity.UserStatusEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfiguration {
    
    private static SessionFactoryConfiguration sessionFactoryConfiguration;
    private final SessionFactory sessionFactory;
    
    private SessionFactoryConfiguration() {
        Configuration configuration = new Configuration().configure("/com/sunrisehms/hibernate.cfg.xml")
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(UserStatusEntity.class);
        sessionFactory = configuration.buildSessionFactory();
    }
    
    public static SessionFactoryConfiguration getInstance() {
        if (sessionFactoryConfiguration == null) {
            sessionFactoryConfiguration = new SessionFactoryConfiguration();
        }
        return sessionFactoryConfiguration;
    }
    
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
