
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.UserStatusEntity;
import com.sunrisehms.repository.custom.UserStatusRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class UserStatusRepositoryImpl implements UserStatusRepository{

    @Override
    public UserStatusEntity get(Session session, Integer Id) throws Exception {
        return session.get(UserStatusEntity.class, Id);
    }

    @Override
    public List<UserStatusEntity> getAll(Session session) throws Exception {
        String hql = "FROM UserStatusEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }

   
    
}
