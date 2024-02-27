package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.RoomDto;
import com.sunrisehms.entity.LogEntity;
import com.sunrisehms.entity.RoomCategoryEntity;
import com.sunrisehms.entity.RoomEntity;
import com.sunrisehms.entity.RoomStatusEntity;
import com.sunrisehms.entity.TaskEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.enums.RoomStatus;
import com.sunrisehms.enums.Task;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.TaskRepository;
import com.sunrisehms.repository.custom.LogRepository;
import com.sunrisehms.repository.custom.RoomCategoryRepository;
import com.sunrisehms.repository.custom.RoomRepository;
import com.sunrisehms.repository.custom.RoomStatusRepository;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.service.custom.RoomService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import com.sunrisehms.util.UserSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class RoomServiceImpl implements RoomService {

    RoomRepository roomRepository = (RoomRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.ROOM);
    RoomCategoryRepository roomCategoryRepository = (RoomCategoryRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.ROOM_CATEGORY);
    RoomStatusRepository roomStatusRepository = (RoomStatusRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.ROOM_STATUS);
    UserRepository userRepository = (UserRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.USER);
    TaskRepository taskRepository = (TaskRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.TASK);
    LogRepository logRepository = (LogRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.LOG);

    @Override
    public Long save(RoomDto roomDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            RoomEntity roomEntity = new RoomEntity();
            roomEntity.setFloor(roomDto.getFloor());
            roomEntity.setRoomNo(roomDto.getRoomNo());
            roomEntity.setPrice(roomDto.getPrice());

            RoomCategoryEntity roomCategoryEntity = roomCategoryRepository.get(session, roomDto.getRoomCategory());
            RoomStatusEntity roomStatusEntity = roomStatusRepository.get(session, roomDto.getRoomStatus());

            roomEntity.setRoomCategory(roomCategoryEntity);
            roomEntity.setRoomStatus(roomStatusEntity);

            Long id = roomRepository.save(session, roomEntity);

            if (id == null) {
                return null;
            }

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.CREATE_ROOM.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();
            return id;

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Room Save Failuer");
        }
    }

    @Override
    public void update(RoomDto roomDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            RoomEntity roomEntity = roomRepository.get(session, roomDto.getId());

            roomEntity.setFloor(roomDto.getFloor());
            roomEntity.setRoomNo(roomDto.getRoomNo());
            roomEntity.setPrice(roomDto.getPrice());

            RoomCategoryEntity roomCategoryEntity = roomCategoryRepository.get(session, roomDto.getRoomCategory());
            RoomStatusEntity roomStatusEntity = roomStatusRepository.get(session, roomDto.getRoomStatus());

            roomEntity.setRoomCategory(roomCategoryEntity);
            roomEntity.setRoomStatus(roomStatusEntity);

            roomRepository.update(session, roomEntity);

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.UPDATE_ROOM.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {

            transaction.rollback();
            throw new Exception("Room Update Failuer");
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            RoomEntity roomEntity = roomRepository.get(session, id);
            RoomStatusEntity roomStatusEntity = roomStatusRepository.get(session, RoomStatus.DELETED.getId());
            roomEntity.setRoomStatus(roomStatusEntity);

            roomRepository.update(session, roomEntity);

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.DELETE_ROOM.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Room Delete Failuer");
        }
    }

    @Override
    public RoomDto get(Long id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            RoomEntity roomEntity = roomRepository.get(session, id);

            if (roomEntity == null) {
                return null;
            }

            RoomDto roomDto = new RoomDto(
                    roomEntity.getId(),
                    roomEntity.getRoomNo(),
                    roomEntity.getFloor(),
                    roomEntity.getPrice(),
                    roomEntity.getRoomStatus().getId(),
                    roomEntity.getRoomCategory().getId()
            );

            try {
                // log
                LogEntity log = new LogEntity();
                log.setDateTime(new Date());
                UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
                log.setUser(user);
                TaskEntity task = taskRepository.get(session, Task.READ_ROOM.getId());
                log.setTask(task);
                logRepository.save(session, log);
                
                transaction.commit();

            } catch (Exception e) {
                transaction.rollback();
                throw new Exception("Log Failier");
            }

            return roomDto;

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Room retrieve Failuer");
        }
    }

    @Override
    public List<RoomDto> getAll() throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        
        List<RoomEntity> roomEntitys = roomRepository.getAll(session);
        List<RoomDto> roomDtos = new ArrayList<>();
        
        for (RoomEntity roomEntity : roomEntitys) {
            roomDtos.add(new RoomDto(
                    roomEntity.getId(),
                    roomEntity.getRoomNo(),
                    roomEntity.getFloor(),
                    roomEntity.getPrice(),
                    roomEntity.getRoomStatus().getId(),
                    roomEntity.getRoomCategory().getId()
            ));
        }
        
        return roomDtos;
    }

    @Override
    public RoomDto getByRoomNo(Integer roomNo) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            RoomEntity roomEntity = roomRepository.getByRoomNo(session, roomNo);

            if (roomEntity == null) {
                return null;
            }

            RoomDto roomDto = new RoomDto(
                    roomEntity.getId(),
                    roomEntity.getRoomNo(),
                    roomEntity.getFloor(),
                    roomEntity.getPrice(),
                    roomEntity.getRoomStatus().getId(),
                    roomEntity.getRoomCategory().getId()
            );

            try {
                // log
                LogEntity log = new LogEntity();
                log.setDateTime(new Date());
                UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
                log.setUser(user);
                TaskEntity task = taskRepository.get(session, Task.READ_ROOM.getId());
                log.setTask(task);
                logRepository.save(session, log);
                
                transaction.commit();

            } catch (Exception e) {
                transaction.rollback();
                throw new Exception("Log Failier");
            }

            return roomDto;

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Room retrieve Failuer");
        }
    }

}
