
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.PrivilegeEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.entity.embedded.PrivilegeID;
import com.sunrisehms.repository.custom.PrivilegeRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PrivilegeRepositoryImpl implements PrivilegeRepository{

    @Override
    public PrivilegeID save(Session session, PrivilegeEntity privilege) throws Exception {
        return (PrivilegeID)session.save(privilege);
    }

    @Override
    public void update(Session session, PrivilegeEntity privilege) throws Exception {
        session.update(privilege);
    }

    @Override
    public void delete(Session session, PrivilegeEntity privilege) throws Exception {
        session.delete(privilege);
    }

    @Override
    public PrivilegeEntity get(Session session, PrivilegeID id) throws Exception {
        return session.get(PrivilegeEntity.class, id);
    }

    @Override
    public List<PrivilegeEntity> getAll(Session session) throws Exception {
        String hql = "FROM PrivilegeEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public List<PrivilegeEntity> getByUser(Session session, UserEntity user) throws Exception {
        String hql = "FROM PrivilegeEntity p WHERE p.id.user=:user";
        Query query = session.createQuery(hql);
        query.setParameter("user", user);
        return query.list();
    }
    
}
