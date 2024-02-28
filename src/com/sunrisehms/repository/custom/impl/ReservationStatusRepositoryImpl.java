/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.ReservationStatusEntity;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author User
 */
public class ReservationStatusRepositoryImpl implements com.sunrisehms.repository.custom.ReservationStatusRepository{

    @Override
    public ReservationStatusEntity get(Session session, Integer Id) throws Exception {
        return session.get(ReservationStatusEntity.class, Id);
    }

    @Override
    public List<ReservationStatusEntity> getAll(Session session) throws Exception {
        String hql = "FROM ReservationStatusEntity";
        Query query = session.createQuery(hql);
        return query.list();
    }
    
}
