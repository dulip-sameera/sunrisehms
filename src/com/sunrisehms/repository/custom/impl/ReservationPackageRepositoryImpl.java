/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.repository.custom.impl;

import com.sunrisehms.entity.ReservationPackageEntity;
import com.sunrisehms.repository.custom.ReservationPackageRepository;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author User
 */
public class ReservationPackageRepositoryImpl implements ReservationPackageRepository{

    @Override
    public ReservationPackageEntity get(Session session, Integer Id) throws Exception {
        return session.get(ReservationPackageEntity.class, Id);
    }

    @Override
    public List<ReservationPackageEntity> getAll(Session session) throws Exception {
        String hql = "FROM ReservationPackageEntity";
        Query query = session.createQuery(hql);
        
        return query.list();
                
    }

    @Override
    public ReservationPackageEntity getByName(Session session, String name) throws Exception {
        String hql = "FROM ReservationPackageEntity r WHERE r.name =: name";
        Query query = session.createQuery(hql);
        query.setParameter("name", name);
        List<ReservationPackageEntity> entitys = query.list();
        if (entitys.isEmpty()) {
            return null;
        } else {
            return entitys.get(0);
        }
    }
    
}
