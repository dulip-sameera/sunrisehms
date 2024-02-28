/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.dto.ReservationDto;
import com.sunrisehms.entity.CustomerEntity;
import com.sunrisehms.entity.LogEntity;
import com.sunrisehms.entity.ReservationEntity;
import com.sunrisehms.entity.ReservedRoomEntity;
import com.sunrisehms.entity.RoomEntity;
import com.sunrisehms.entity.TaskEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.entity.embedded.ReservedRoomId;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.enums.ReservationStatus;
import com.sunrisehms.enums.RoomStatus;
import com.sunrisehms.enums.Task;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.TaskRepository;
import com.sunrisehms.repository.custom.CustomerRepository;
import com.sunrisehms.repository.custom.LogRepository;
import com.sunrisehms.repository.custom.ReservationPackageRepository;
import com.sunrisehms.repository.custom.ReservationRepository;
import com.sunrisehms.repository.custom.ReservationStatusRepository;
import com.sunrisehms.repository.custom.ReservedRoomRepository;
import com.sunrisehms.repository.custom.RoomRepository;
import com.sunrisehms.repository.custom.RoomStatusRepository;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.service.custom.ReservationService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import com.sunrisehms.util.UserSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author User
 */
public class ReservationServiceImpl implements ReservationService {

    UserRepository userRepository = (UserRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.USER);
    TaskRepository taskRepository = (TaskRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.TASK);
    LogRepository logRepository = (LogRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.LOG);
    ReservationRepository reservationRepository = (ReservationRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.RESERVATION);
    ReservationStatusRepository reservationStatusRepository = (ReservationStatusRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.RESERVATION_STATUS);
    CustomerRepository customerRepository = (CustomerRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.CUSTOMER);
    ReservationPackageRepository reservationPackageRepository = (ReservationPackageRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.RESERVATION_PACKAGE);
    RoomRepository roomRepository = (RoomRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.ROOM);
    RoomStatusRepository roomStatusRepository = (RoomStatusRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.ROOM_STATUS);
    ReservedRoomRepository reservedRoomRepository = (ReservedRoomRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.RESERVED_ROOM);

    @Override
    public Long save(ReservationDto reservationDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            ReservationEntity entity = new ReservationEntity();
            entity.setCustomerEntity(customerRepository.get(session, reservationDto.getCustomerDto().getId()));
            entity.setEnd(reservationDto.getTo());
            entity.setStart(reservationDto.getFrom());
            entity.setPrice(reservationDto.getPrice());
            entity.setReservationPackageEntity(reservationPackageRepository.get(session, reservationDto.getPackageId()));
            entity.setReservationStatusEntity(reservationStatusRepository.get(session, reservationDto.getStatus()));

            Long id = reservationRepository.save(session, entity);

            if (id == null) {
                throw new Exception("reservation creation failed");
            }

            ReservationEntity reservationEntity = reservationRepository.get(session, id);

            List<Long> roomIds = reservationDto.getRoomIds();
            List<ReservedRoomEntity> reservedRoomEntitys = new ArrayList<>();

            for (Long roomId : roomIds) {

                ReservedRoomEntity reservedRoomEntity = new ReservedRoomEntity();
                RoomEntity roomEntity = roomRepository.get(session, roomId);
                roomEntity.setRoomStatus(roomStatusRepository.get(session, RoomStatus.RESERVED.getId()));
                roomRepository.update(session, roomEntity);
                ReservedRoomId reservedRoomId = new ReservedRoomId(reservationEntity, roomEntity);
                reservedRoomEntity.setReservedRoomId(reservedRoomId);
                reservedRoomEntitys.add(reservedRoomEntity);

            }

            reservedRoomEntitys.forEach(System.out::println);

            for (ReservedRoomEntity reservedRoomEntity : reservedRoomEntitys) {

                reservedRoomRepository.save(session, reservedRoomEntity);

            }

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.CREATE_RESERVATION.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

            return id;

        } catch (Exception e) {

            transaction.rollback();
            throw new Exception("Reservation save failed");

        }
    }

    @Override
    public void update(ReservationDto reservationDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            ReservationEntity reservationEntity = reservationRepository.get(session, reservationDto.getId());

            List<ReservedRoomEntity> oldReservedRooms = reservedRoomRepository.getByReservation(session, reservationEntity);
            reservationEntity.setCustomerEntity(customerRepository.get(session, reservationDto.getCustomerDto().getId()));
            reservationEntity.setEnd(reservationDto.getTo());
            reservationEntity.setPrice(reservationDto.getPrice());
            reservationEntity.setReservationPackageEntity(reservationPackageRepository.get(session, reservationDto.getPackageId()));
            reservationEntity.setStart(reservationDto.getFrom());
            reservationEntity.setReservationStatusEntity(reservationStatusRepository.get(session, reservationDto.getStatus()));

            reservationRepository.update(session, reservationEntity);

            List<Long> roomIds = reservationDto.getRoomIds();
            List<ReservedRoomEntity> reservedRoomEntitys = new ArrayList<>();

            for (Long roomId : roomIds) {

                ReservedRoomEntity reservedRoomEntity = new ReservedRoomEntity();
                RoomEntity roomEntity = roomRepository.get(session, roomId);
                
                roomEntity.setRoomStatus(roomStatusRepository.get(session, RoomStatus.RESERVED.getId()));
                
                roomRepository.update(session, roomEntity);
                
                ReservedRoomId reservedRoomId = new ReservedRoomId(reservationEntity, roomEntity);
                reservedRoomEntity.setReservedRoomId(reservedRoomId);
                reservedRoomEntity.setCheckIn(reservationDto.getCheckIn());
                reservedRoomEntity.setCheckOut(reservationDto.getCheckOut());
                reservedRoomEntitys.add(reservedRoomEntity);

            }

            //set the status of the old rooms to available
            oldReservedRooms.forEach((oldReservedRoom) -> {
                try {
                    RoomEntity room = oldReservedRoom.getReservedRoomId().getRoomEntity();
                    room.setRoomStatus(roomStatusRepository.get(session, RoomStatus.AVAILABLE.getId()));
                    roomRepository.update(session, room);
                } catch (Exception ex) {
                    Logger.getLogger(ReservationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            // delete old rooms
            for (ReservedRoomEntity oldReservedRoom : oldReservedRooms) {
                reservedRoomRepository.delete(session, oldReservedRoom);
            }

            // save new rooms
            for (ReservedRoomEntity reservedRoomEntity : reservedRoomEntitys) {
                reservedRoomRepository.save(session, reservedRoomEntity);
            }

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.UPDATE_RESERVATION.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Reservation Update Failed");
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            ReservationEntity reservationEntity = reservationRepository.get(session, id);

            reservationEntity.setReservationStatusEntity(reservationStatusRepository.get(session, ReservationStatus.DELETED.getId()));

            reservationRepository.update(session, reservationEntity);

            // set the status of all rooms that link to this reservation back to available
            List<ReservedRoomEntity> reservedRooms = reservedRoomRepository.getByReservation(session, reservationEntity);
            List<RoomEntity> rooms = new ArrayList<>();
            reservedRooms.forEach((reservedRoom) -> {
                try {
                    RoomEntity room = reservedRoom.getReservedRoomId().getRoomEntity();
                    room.setRoomStatus(roomStatusRepository.get(session, RoomStatus.AVAILABLE.getId()));
                    roomRepository.update(session, room);
                } catch (Exception ex) {
                    System.out.println("RESERVATION DELETE FAILED: ROOM STATUS UPDATE FAILED");
                    Logger.getLogger(ReservationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.DELETE_RESERVATION.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {

            transaction.rollback();
            throw new Exception("Reservation Deletion Failed");
        }
    }

    @Override
    public ReservationDto get(Long id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        ReservationEntity reservationEntity = reservationRepository.get(session, id);

        if (reservationEntity == null) {
            return null;
        }

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setFrom(reservationEntity.getStart());
        reservationDto.setTo(reservationEntity.getEnd());
        reservationDto.setId(reservationEntity.getId());
        reservationDto.setPackageId(reservationEntity.getReservationPackageEntity().getId());
        reservationDto.setStatus(reservationEntity.getReservationStatusEntity().getId());
        reservationDto.setPrice(reservationEntity.getPrice());

        CustomerEntity customerEntity = reservationEntity.getCustomerEntity();
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerEntity.getId());
        customerDto.setFirstName(customerEntity.getCustomerName().getFirstName());
        customerDto.setMiddleName(customerEntity.getCustomerName().getMiddleName());
        customerDto.setLastName(customerEntity.getCustomerName().getLastName());
        customerDto.setNic(customerEntity.getNic());
        customerDto.setPhone(customerEntity.getPhone());
        customerDto.setEmail(customerEntity.getEmail());
        customerDto.setStatus(customerEntity.getCustomerStatus().getId());

        reservationDto.setCustomerDto(customerDto);

        List<Long> roomids = new ArrayList<>();
        List<ReservedRoomEntity> reservedRoomEntitys = reservedRoomRepository.getByReservation(session, reservationEntity);;
        for (ReservedRoomEntity reservedRoomEntity : reservedRoomEntitys) {
            roomids.add(reservedRoomEntity.getReservedRoomId().getRoomEntity().getId());
        }
        reservationDto.setCheckIn(reservedRoomEntitys.get(0).getCheckIn());
        reservationDto.setCheckOut(reservedRoomEntitys.get(0).getCheckOut());
        reservationDto.setRoomIds(roomids);

        // log
        LogEntity log = new LogEntity();
        log.setDateTime(new Date());
        UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
        log.setUser(user);
        TaskEntity task = taskRepository.get(session, Task.READ_RESERVATION.getId());
        log.setTask(task);
        logRepository.save(session, log);

        return reservationDto;

    }

    @Override
    public List<ReservationDto> getAll() throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        List<ReservationEntity> reservationEntitys = reservationRepository.getAll(session);
        List<ReservationDto> reservationDtos = new ArrayList<>();

        for (ReservationEntity reservationEntity : reservationEntitys) {

            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setFrom(reservationEntity.getStart());
            reservationDto.setTo(reservationEntity.getEnd());
            reservationDto.setId(reservationEntity.getId());
            reservationDto.setPackageId(reservationEntity.getReservationPackageEntity().getId());
            reservationDto.setStatus(reservationEntity.getReservationStatusEntity().getId());
            reservationDto.setPrice(reservationEntity.getPrice());

            CustomerEntity customerEntity = reservationEntity.getCustomerEntity();
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customerEntity.getId());
            customerDto.setFirstName(customerEntity.getCustomerName().getFirstName());
            customerDto.setMiddleName(customerEntity.getCustomerName().getMiddleName());
            customerDto.setLastName(customerEntity.getCustomerName().getLastName());
            customerDto.setNic(customerEntity.getNic());
            customerDto.setPhone(customerEntity.getPhone());
            customerDto.setEmail(customerEntity.getEmail());
            customerDto.setStatus(customerEntity.getCustomerStatus().getId());

            reservationDto.setCustomerDto(customerDto);

            List<Long> roomids = new ArrayList<>();
            List<ReservedRoomEntity> reservedRoomEntitys = reservedRoomRepository.getByReservation(session, reservationEntity);
            if (reservedRoomEntitys.isEmpty()) {
                roomids = null;
            } else {
                
                for (ReservedRoomEntity reservedRoomEntity : reservedRoomEntitys) {
                    roomids.add(reservedRoomEntity.getReservedRoomId().getRoomEntity().getId());
                }
            }
            reservationDto.setRoomIds(roomids);
            reservationDto.setCheckIn(reservedRoomEntitys.get(0).getCheckIn());
            reservationDto.setCheckOut(reservedRoomEntitys.get(0).getCheckOut());

            reservationDtos.add(reservationDto);

        }

        return reservationDtos;
    }

    @Override
    public void checkIn(ReservationDto reservationDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            ReservationEntity reservationEntity = reservationRepository.get(session, reservationDto.getId());
            reservationEntity.setReservationStatusEntity(reservationStatusRepository.get(session, ReservationStatus.SHOW.getId()));

            List<Long> roomIds = reservationDto.getRoomIds();

            for (Long roomId : roomIds) {

                try {
                    ReservedRoomEntity reservedRoomEntity = new ReservedRoomEntity();
                    RoomEntity roomEntity = roomRepository.get(session, roomId);
                    roomEntity.setRoomStatus(roomStatusRepository.get(session, RoomStatus.OCCUPIED.getId()));
                    roomRepository.update(session, roomEntity);

                    // add changing room status if this didn't set it automatically
                    ReservedRoomId reservedRoomId = new ReservedRoomId(reservationEntity, roomEntity);
                    reservedRoomEntity.setReservedRoomId(reservedRoomId);
                    reservedRoomEntity.setCheckIn(new Date());
                    reservedRoomRepository.update(session, reservedRoomEntity);

                } catch (Exception e) {

                    throw new Exception("CheckIn Failed: Failed to update check in time");
                }

            }

            reservationRepository.update(session, reservationEntity);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("CheckIN Failed");
        }
    }

    @Override
    public void checkOut(ReservationDto reservationDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            ReservationEntity reservationEntity = reservationRepository.get(session, reservationDto.getId());
            reservationEntity.setReservationStatusEntity(reservationStatusRepository.get(session, ReservationStatus.SHOW.getId()));

            List<Long> roomIds = reservationDto.getRoomIds();

            for (Long roomId : roomIds) {

                try {
                    ReservedRoomEntity reservedRoomEntity = new ReservedRoomEntity();
                    RoomEntity roomEntity = roomRepository.get(session, roomId);
                    roomEntity.setRoomStatus(roomStatusRepository.get(session, RoomStatus.AVAILABLE.getId()));
                    roomRepository.update(session, roomEntity);
                    
                    ReservedRoomId reservedRoomId = new ReservedRoomId(reservationEntity, roomEntity);
                    reservedRoomEntity.setReservedRoomId(reservedRoomId);
                    reservedRoomEntity.setCheckIn(reservationDto.getCheckIn());
                    reservedRoomEntity.setCheckOut(new Date());
                    
                    reservedRoomRepository.update(session, reservedRoomEntity);

                } catch (Exception e) {

                    throw new Exception("CheckIn Failed: Failed to update check in time");
                }

            }

            reservationRepository.update(session, reservationEntity);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("CheckOut Failed");
        }

    }

    @Override
    public void cancelReservation(ReservationDto reservationDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            ReservationEntity reservationEntity = reservationRepository.get(session, reservationDto.getId());
            reservationEntity.setReservationStatusEntity(reservationStatusRepository.get(session, ReservationStatus.CANCELLED.getId()));

            List<Long> roomIds = reservationDto.getRoomIds();

            for (Long roomId : roomIds) {

                try {
                    ReservedRoomEntity reservedRoomEntity = new ReservedRoomEntity();
                    RoomEntity roomEntity = roomRepository.get(session, roomId);
                    roomEntity.setRoomStatus(roomStatusRepository.get(session, RoomStatus.AVAILABLE.getId()));
                    roomRepository.update(session, roomEntity);
                    
                    ReservedRoomId reservedRoomId = new ReservedRoomId(reservationEntity, roomEntity);
                    reservedRoomEntity.setReservedRoomId(reservedRoomId);
                    reservedRoomEntity.setCheckIn(reservationDto.getCheckIn());
                    reservedRoomEntity.setCheckOut(new Date());
                    
                    reservedRoomRepository.update(session, reservedRoomEntity);

                } catch (Exception e) {

                    throw new Exception("Cancel Reservation Failed: Failed to update check in time");
                }

            }

            reservationRepository.update(session, reservationEntity);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Cancel Reservation Failed");
        }
    }

}
