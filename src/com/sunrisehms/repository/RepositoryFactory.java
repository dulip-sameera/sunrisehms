
package com.sunrisehms.repository;

import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.repository.custom.impl.LogRepositoryImpl;
import com.sunrisehms.repository.custom.impl.PrivilegeRepositoryImpl;
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
            default ->
                null;
        };
    }
}
