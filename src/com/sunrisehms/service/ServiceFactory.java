
package com.sunrisehms.service;

import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.custom.impl.CustomerServiceImpl;
import com.sunrisehms.service.custom.impl.LoginServiceImpl;
import com.sunrisehms.service.custom.impl.ReservationPackageServiceImpl;
import com.sunrisehms.service.custom.impl.ReservationServiceImpl;
import com.sunrisehms.service.custom.impl.RoomCategoryServiceImpl;
import com.sunrisehms.service.custom.impl.RoomServiceImpl;
import com.sunrisehms.service.custom.impl.TaskServiceImpl;
import com.sunrisehms.service.custom.impl.UserServiceImpl;

public class ServiceFactory {
    
    private static ServiceFactory serviceFactory;
    
    private ServiceFactory(){}
    
    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        
        return serviceFactory;
    }
    
    public Service getService(ServiceType type) {
        return switch (type) {
            case LOGIN -> new LoginServiceImpl();
            case USER -> new UserServiceImpl();
            case TASK -> new TaskServiceImpl();
            case ROOM_CATEGORY -> new RoomCategoryServiceImpl();
            case ROOM -> new RoomServiceImpl();
            case CUSTOMER -> new CustomerServiceImpl();
            case RESERVATION -> new ReservationServiceImpl();
            case RESERVATION_PACKAGE -> new ReservationPackageServiceImpl();
            default -> null;
        };
    } 
    
    
}
