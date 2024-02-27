
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.repository.custom.UserRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserRepositoryImpl implements UserRepository{

    @Override
    public Long save(Session session, UserEntity user) throws Exception {
        return (Long)session.save(user);
    }

    @Override
    public void update(Session session, UserEntity user) throws Exception {
        session.update(user);
    }

    @Override
    public void delete(Session session, UserEntity user) throws Exception {
        session.delete(user);
    }

    @Override
    public UserEntity get(Session session, Long id) throws Exception {
        return session.get(UserEntity.class, id);
    }

    @Override
    public List<UserEntity> getAll(Session session) throws Exception {
        String hql = "FROM UserEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public UserEntity getByUserName(Session session, String username) throws Exception {
        String hql = "FROM UserEntity u WHERE u.userName=:user_name";
        Query query = session.createQuery(hql);
        query.setParameter("user_name", username);
        List<UserEntity> users = query.list();
        
        if(!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Override
    public UserEntity getById(Session session, Long id) throws Exception {
        String hql = "FROM UserEntity u WHERE u.id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("user_name", id);
        List<UserEntity> users = query.list();
        if(!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }        
    }
}
