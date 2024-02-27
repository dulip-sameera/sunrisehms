
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.RoomCategoryEntity;
import com.sunrisehms.repository.custom.RoomCategoryRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class RoomCategoryRepositoryImpl implements RoomCategoryRepository{

    @Override
    public Integer save(Session session, RoomCategoryEntity entity) throws Exception {
        return (Integer)session.save(entity);
    }

    @Override
    public void update(Session session, RoomCategoryEntity entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void delete(Session session, RoomCategoryEntity entity) throws Exception {
        session.delete(entity);
    }

    @Override
    public RoomCategoryEntity get(Session session, Integer id) throws Exception {
        return session.get(RoomCategoryEntity.class, id);
    }

    @Override
    public List<RoomCategoryEntity> getAll(Session session) throws Exception {
        String hql = "From RoomCategoryEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }

    
    
}
