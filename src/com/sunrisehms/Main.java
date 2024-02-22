
package com.sunrisehms;

import com.sunrisehms.util.SessionFactoryConfiguration;
import org.hibernate.Session;

public class Main {

    public static void main(String[] args) {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
    }
    
}
