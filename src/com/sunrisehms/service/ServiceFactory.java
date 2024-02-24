
package com.sunrisehms.service;

import com.sunrisehms.enums.ServiceType;
import com.sunrisehms.service.custom.impl.LoginServiceImpl;

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
            default -> null;
        };
    } 
    
    
}
