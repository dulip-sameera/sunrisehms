/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sunrisehms.service.custom;


import com.sunrisehms.dto.ReservationPackageDto;
import com.sunrisehms.service.Service;
import java.util.List;

/**
 *
 * @author User
 */
public interface ReservationPackageService extends Service{
    
    ReservationPackageDto get(Integer id) throws Exception;
    List<ReservationPackageDto> getAll() throws Exception;
    ReservationPackageDto getByName(String name) throws Exception;
    
}
