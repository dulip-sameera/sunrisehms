
package com.sunrisehms.enums;

public enum Task {
    
    LOGIN(1, "LOGIN"),
    LOGOUT(2, "LOGOUT"),
    CREATE_USER(3, "CREATE USER"),
    READ_USER(4, "READ USER"),
    UPDATE_USER(5, "UPDATE USER"),
    DELETE_USER(6, "DELETE USER"),
    CREATE_ROOM_CATEGORY(7, "CREATE ROOM CATEGORY"),
    READ_ROOM_CATEGORY(8, "READ ROOM CATEGORY"),
    UPDATE_ROOM_CATEGORY(9, "UPDATE ROOM CATEGORY"),
    DELETE_ROOM_CATEGORY(10, "DELETE ROOM CATEGORY"),
    CREATE_ROOM(11, "CREATE ROOM"),
    READ_ROOM(12, "READ ROOM"),
    UPDATE_ROOM(13, "UPDATE ROOM"),
    DELETE_ROOM(14, "DELETE ROOM"),
    CREATE_CUSTOMER(15, "CREATE CUSTOMER"),
    READ_CUSTOMER(16, "READ CUSTOMER"),
    UPDATE_CUSTOMER(17, "UPDATE CUSTOMER"),
    DELETE_CUSTOMER(18, "DELETE CUSTOMER"),
    CREATE_RESERVATION(19, "CREATE RESERVATION"),
    READ_RESERVATION(20, "READ RESERVATION"),
    UPDATE_RESERVATION(21, "UPDATE RESERVATION"),
    DELETE_RESERVATION(22, "DELETE RESERVATION"),
    CANCEL_RESERVATION(23, "CANCEL RESERVATION");
    
    private final Integer id;
    private final String name;
    
    private Task(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
}
