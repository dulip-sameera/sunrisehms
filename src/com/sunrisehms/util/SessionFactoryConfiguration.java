
package com.sunrisehms.util;

import com.sunrisehms.entity.CustomerEntity;
import com.sunrisehms.entity.CustomerStatus;
import com.sunrisehms.entity.LogEntity;
import com.sunrisehms.entity.PrivilegeEntity;
import com.sunrisehms.entity.ReservationEntity;
import com.sunrisehms.entity.ReservationPackageEntity;
import com.sunrisehms.entity.ReservationStatusEntity;
import com.sunrisehms.entity.ReservedRoomEntity;
import com.sunrisehms.entity.RoomCategoryEntity;
import com.sunrisehms.entity.RoomEntity;
import com.sunrisehms.entity.RoomStatusEntity;
import com.sunrisehms.entity.TaskEntity;
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
                .addAnnotatedClass(UserStatusEntity.class)
                .addAnnotatedClass(TaskEntity.class)
                .addAnnotatedClass(PrivilegeEntity.class)
                .addAnnotatedClass(LogEntity.class)
                .addAnnotatedClass(RoomCategoryEntity.class)
                .addAnnotatedClass(RoomStatusEntity.class)
                .addAnnotatedClass(RoomEntity.class)
                .addAnnotatedClass(CustomerStatus.class)
                .addAnnotatedClass(CustomerEntity.class)
                .addAnnotatedClass(ReservationStatusEntity.class)
                .addAnnotatedClass(ReservationEntity.class)
                .addAnnotatedClass(ReservedRoomEntity.class)
                .addAnnotatedClass(ReservationPackageEntity.class);
        
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
