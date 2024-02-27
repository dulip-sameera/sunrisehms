/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.RoomEntity;
import com.sunrisehms.repository.custom.RoomRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author User
 */
public class RoomRepositoryImpl implements RoomRepository{

    @Override
    public Long save(Session session, RoomEntity entity) throws Exception {
        return (Long)session.save(entity);
    }

    @Override
    public void update(Session session, RoomEntity entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void delete(Session session, RoomEntity entity) throws Exception {
        session.delete(entity);
    }

    @Override
    public RoomEntity get(Session session, Long id) throws Exception {
        return session.get(RoomEntity.class, id);
    }

    @Override
    public List<RoomEntity> getAll(Session session) throws Exception {
        String hql = "FROM RoomEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public RoomEntity getByRoomNo(Session session, Integer roomNo) throws Exception {
        String hql = "FROM RoomEntity r where r.roomNo=:roomNo";
        Query query = session.createQuery(hql);
        query.setParameter("roomNo", roomNo);
        List<RoomEntity> rooms = query.list();
        
        if (rooms.isEmpty()) {
            return null;
        } else {
            return rooms.get(0);
        }
    }
    
}
