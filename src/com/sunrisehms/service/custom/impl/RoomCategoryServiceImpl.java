package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.entity.LogEntity;
import com.sunrisehms.entity.RoomCategoryEntity;
import com.sunrisehms.entity.TaskEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.enums.Task;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.TaskRepository;
import com.sunrisehms.repository.custom.LogRepository;
import com.sunrisehms.repository.custom.RoomCategoryRepository;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.service.custom.RoomCategoryService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import com.sunrisehms.util.UserSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RoomCategoryServiceImpl implements RoomCategoryService {

    RoomCategoryRepository roomCategoryRepository = (RoomCategoryRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.ROOM_CATEGORY);
    UserRepository userRepository = (UserRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.USER);
    TaskRepository taskRepository = (TaskRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.TASK);
    LogRepository logRepository = (LogRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.LOG);

    @Override
    public Integer save(RoomCategoryDto roomCategoryDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            RoomCategoryEntity newRoomCategory = new RoomCategoryEntity();
            
            newRoomCategory.setName(roomCategoryDto.getName());
            newRoomCategory.setNoOfBeds(roomCategoryDto.getNoOfBeds());
            newRoomCategory.setPrice(roomCategoryDto.getPrice());
            newRoomCategory.setAc(roomCategoryDto.getAc());

            Integer roomCatId = roomCategoryRepository.save(session, newRoomCategory);

            if (roomCatId == null) {
                return null;
            }

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.CREATE_ROOM_CATEGORY.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();
            return roomCatId;

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Room Category Save Failed");
        }
    }

    @Override
    public void update(RoomCategoryDto roomCategoryDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            RoomCategoryEntity roomCategory = roomCategoryRepository.get(session, roomCategoryDto.getId());

            roomCategory.setAc(roomCategoryDto.getAc());
            roomCategory.setName(roomCategoryDto.getName());
            roomCategory.setNoOfBeds(roomCategory.getNoOfBeds());
            roomCategory.setPrice(roomCategoryDto.getPrice());

            roomCategoryRepository.update(session, roomCategory);

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.UPDATE_ROOM_CATEGORY.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Room Category Update Failure....!");
        }
    }

    @Override
    public void delete(Integer id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            RoomCategoryEntity roomCategory = roomCategoryRepository.get(session, id);

            roomCategoryRepository.delete(session, roomCategory);

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.DELETE_ROOM_CATEGORY.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Room Category Delete Failure....!");
        }
    }

    @Override
    public RoomCategoryDto get(Integer id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            RoomCategoryEntity roomCategoryEntity = roomCategoryRepository.get(session, id);
            
            if (roomCategoryEntity == null) {
                return null;
            }

            try {
                // log
                LogEntity log = new LogEntity();
                log.setDateTime(new Date());
                UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
                log.setUser(user);
                TaskEntity task = taskRepository.get(session, Task.READ_ROOM_CATEGORY.getId());
                log.setTask(task);
                logRepository.save(session, log);
                
                transaction.commit();
                
            } catch (Exception e) {
                transaction.rollback();
                throw new Exception("Room Category Log Failure");
                
            }


            return new RoomCategoryDto(
                    roomCategoryEntity.getId(),
                    roomCategoryEntity.getName(),
                    roomCategoryEntity.getNoOfBeds(),
                    roomCategoryEntity.getPrice(),
                    roomCategoryEntity.getAc()
            );
        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Room Category Retrieve Failure....!");
        }

    }

    @Override
    public List<RoomCategoryDto> getAll() throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        List<RoomCategoryEntity> roomCategoryEntitys = roomCategoryRepository.getAll(session);

        List<RoomCategoryDto> roomCategoryDtos = new ArrayList<>();

        for (RoomCategoryEntity roomCategoryEntity : roomCategoryEntitys) {
            roomCategoryDtos.add(new RoomCategoryDto(
                    roomCategoryEntity.getId(),
                    roomCategoryEntity.getName(),
                    roomCategoryEntity.getNoOfBeds(),
                    roomCategoryEntity.getPrice(),
                    roomCategoryEntity.getAc()
            ));
        }

        return roomCategoryDtos;
    }

}
