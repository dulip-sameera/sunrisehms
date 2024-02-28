/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.ReservationEntity;
import com.sunrisehms.entity.ReservedRoomEntity;
import com.sunrisehms.entity.embedded.ReservedRoomId;
import com.sunrisehms.repository.custom.ReservedRoomRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author User
 */
public class ReservedRoomRepositoryImpl implements ReservedRoomRepository{

    @Override
    public ReservedRoomId save(Session session, ReservedRoomEntity entity) throws Exception {
        return (ReservedRoomId)session.save(entity);
    }

    @Override
    public void update(Session session, ReservedRoomEntity entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void delete(Session session, ReservedRoomEntity entity) throws Exception {
        session.delete(entity);
    }

    @Override
    public ReservedRoomEntity get(Session session, ReservedRoomId id) throws Exception {
        return session.get(ReservedRoomEntity.class, id);
    }

    @Override
    public List<ReservedRoomEntity> getAll(Session session) throws Exception {
        String hql = "FROM ReservedRoomEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public List<ReservedRoomEntity> getByReservation(Session session, ReservationEntity reservationEntity) throws Exception {
        String hql = "FROM ReservedRoomEntity r WHERE r.reservedRoomId.reservationEntity=:reservation";
        Query query = session.createQuery(hql);
        query.setParameter("reservation", reservationEntity);
        return query.list();
    }
    
}
