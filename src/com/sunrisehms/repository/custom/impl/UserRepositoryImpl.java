
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
}
