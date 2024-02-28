/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sunrisehms.service.custom;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.dto.ReservationDto;
import com.sunrisehms.service.Service;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public interface ReservationService extends Service{
    Long save(ReservationDto reservationDto) throws Exception;
    void update(ReservationDto reservationDto) throws Exception;
    void delete(Long id) throws Exception;
    ReservationDto get(Long id) throws Exception;
    List<ReservationDto> getAll() throws Exception;
    void checkIn(ReservationDto reservationDto) throws Exception;
    void checkOut(ReservationDto reservationDto) throws Exception;
    void cancelReservation(ReservationDto reservationDto) throws Exception;
}
