/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.ReservationPackageEntity;
import com.sunrisehms.repository.ReadRepository;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public interface ReservationPackageRepository extends ReadRepository<ReservationPackageEntity, Integer>{
    ReservationPackageEntity getByName(Session session, String name) throws Exception;
}
