package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.RoomStatusEntity;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class RoomStatusRepositoryImpl implements com.sunrisehms.repository.custom.RoomStatusRepository{

    @Override
    public RoomStatusEntity get(Session session, Integer Id) throws Exception {
        return session.get(RoomStatusEntity.class, Id);
    }

    @Override
    public List<RoomStatusEntity> getAll(Session session) throws Exception {
        String hql = "FROM RoomStatusEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }
    
}
