package com.sunrisehms.repository;

import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.repository.custom.impl.CustomerRepositoryImpl;
import com.sunrisehms.repository.custom.impl.CustomerStatusRepositoryImpl;
import com.sunrisehms.repository.custom.impl.LogRepositoryImpl;
import com.sunrisehms.repository.custom.impl.PrivilegeRepositoryImpl;
import com.sunrisehms.repository.custom.impl.ReservationPackageRepositoryImpl;
import com.sunrisehms.repository.custom.impl.ReservationRepositoryImpl;
import com.sunrisehms.repository.custom.impl.ReservationStatusRepositoryImpl;
import com.sunrisehms.repository.custom.impl.ReservedRoomRepositoryImpl;
import com.sunrisehms.repository.custom.impl.RoomCategoryRepositoryImpl;
import com.sunrisehms.repository.custom.impl.RoomRepositoryImpl;
import com.sunrisehms.repository.custom.impl.RoomStatusRepositoryImpl;
import com.sunrisehms.repository.custom.impl.TaskRepositoryImpl;
import com.sunrisehms.repository.custom.impl.UserRepositoryImpl;
import com.sunrisehms.repository.custom.impl.UserStatusRepositoryImpl;

public class RepositoryFactory {

    private static RepositoryFactory repositoryFactory;

    private RepositoryFactory() {

    }

    public static RepositoryFactory getInstance() {
        if (repositoryFactory == null) {
            repositoryFactory = new RepositoryFactory();
        }

        return repositoryFactory;
    }

    public Repository getRepository(RepositoryType type) {
        return switch (type) {
            case USER ->
                new UserRepositoryImpl();
            case PRIVILEGE ->
                new PrivilegeRepositoryImpl();
            case LOG ->
                new LogRepositoryImpl();
            case USER_STATUS ->
                new UserStatusRepositoryImpl();
            case TASK ->
                new TaskRepositoryImpl();
            case ROOM_CATEGORY ->
                new RoomCategoryRepositoryImpl();
            case ROOM_STATUS ->
                new RoomStatusRepositoryImpl();
            case ROOM ->
                new RoomRepositoryImpl();
            case CUSTOMER_STATUS ->
                new CustomerStatusRepositoryImpl();
            case CUSTOMER ->
                new CustomerRepositoryImpl();
            case RESERVATION -> 
                new ReservationRepositoryImpl();
            case RESERVATION_STATUS -> 
                new ReservationStatusRepositoryImpl();
            case RESERVATION_PACKAGE ->
                new ReservationPackageRepositoryImpl();
            case RESERVED_ROOM -> 
                new ReservedRoomRepositoryImpl();
           
            default ->
                null;
        };
    }
}
