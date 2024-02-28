/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.ReservationEntity;
import com.sunrisehms.entity.ReservedRoomEntity;
import com.sunrisehms.entity.embedded.ReservedRoomId;
import com.sunrisehms.repository.CrudRepository;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public interface ReservedRoomRepository extends CrudRepository<ReservedRoomEntity, ReservedRoomId>{
    List<ReservedRoomEntity> getByReservation(Session session, ReservationEntity reservationEntity) throws Exception;
            
}
