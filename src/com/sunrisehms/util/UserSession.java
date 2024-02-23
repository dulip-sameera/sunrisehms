
package com.sunrisehms.util;

import com.sunrisehms.dto.UserDto;

public class UserSession {
    private static UserSession userSession;
    
    private UserDto user;
    
    private UserSession(){}
    
    public static UserSession getInstance() {
        if (userSession == null) {
            userSession = new UserSession();
        }
        return userSession;
    }
    
    public UserDto getUser() {
        return user;
    }
    
    public void setUser(UserDto user) {
        this.user = user;
    }
}
