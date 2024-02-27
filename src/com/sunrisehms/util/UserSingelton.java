
package com.sunrisehms.util;

public class UserSingelton {
    private static UserSingelton userSingelton;
    
    private UserTableRow userTableRowData;
    
    private UserSingelton() {};
    
    public static UserSingelton getInstance() {
        if (userSingelton == null) {
            userSingelton = new UserSingelton();
        }
        
        return userSingelton;
    }
    
    public void setUserData(UserTableRow userData) {
        this.userTableRowData = userData;
    } 
    
    public UserTableRow getUserData() {
        return this.userTableRowData;
    }
    
}
