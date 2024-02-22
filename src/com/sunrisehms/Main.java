
package com.sunrisehms;

import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.util.SessionFactoryConfiguration;
import org.hibernate.Session;

public class Main {

    public static void main(String[] args) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        
    }
    
}
