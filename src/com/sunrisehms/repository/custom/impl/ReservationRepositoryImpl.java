/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.ReservationEntity;
import com.sunrisehms.repository.custom.ReservationRepository;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author User
 */
public class ReservationRepositoryImpl implements ReservationRepository{

    @Override
    public Long save(Session session, ReservationEntity entity) throws Exception {
        return (Long)session.save(entity);
    }

    @Override
    public void update(Session session, ReservationEntity entity) throws Exception {
        session.update(entity);
    }

    @Override
    public void delete(Session session, ReservationEntity entity) throws Exception {
        session.delete(entity);
    }

    @Override
    public ReservationEntity get(Session session, Long id) throws Exception {
        return session.get(ReservationEntity.class, id);
    }

    @Override
    public List<ReservationEntity> getAll(Session session) throws Exception {
        String hql = "FROM ReservationEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }
    
}
