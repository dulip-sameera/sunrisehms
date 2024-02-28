/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.ReservationPackageDto;
import com.sunrisehms.entity.ReservationPackageEntity;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.custom.ReservationPackageRepository;
import com.sunrisehms.service.custom.ReservationPackageService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author User
 */
public class ReservationPackageServiceImpl implements ReservationPackageService {

    ReservationPackageRepository reservationPackageRepository = (ReservationPackageRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.RESERVATION_PACKAGE);

    @Override
    public ReservationPackageDto get(Integer id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        ReservationPackageEntity entity = reservationPackageRepository.get(session, id);

        if (entity == null) {
            return null;
        }

        ReservationPackageDto dto = new ReservationPackageDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());

        return dto;
    }

    @Override
    public List<ReservationPackageDto> getAll() throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        List<ReservationPackageEntity> entitys = reservationPackageRepository.getAll(session);

        List<ReservationPackageDto> dtos = new ArrayList<>();

        for (ReservationPackageEntity entity : entitys) {
            ReservationPackageDto dto = new ReservationPackageDto();

            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setPrice(entity.getPrice());

            dtos.add(dto);
        }

        return dtos;

    }

    @Override
    public ReservationPackageDto getByName(String name) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        ReservationPackageEntity entity = reservationPackageRepository.getByName(session, name);
        
        if (entity == null) {
            return null;
        }
        
        ReservationPackageDto dto = new ReservationPackageDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPrice(entity.getPrice());

        return dto;
    }

}
