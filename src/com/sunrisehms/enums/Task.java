
package com.sunrisehms.enums;

public enum Task {
    
    LOGIN(1),
    LOGOUT(2),
    CREATE_USER(3),
    READ_USER(4),
    UPDATE_USER(5),
    DELETE_USER(6),
    CREATE_ROOM_CATEGORY(7),
    READ_ROOM_CATEGORY(8),
    UPDATE_ROOM_CATEGORY(9),
    DELETE_ROOM_CATEGORY(10),
    CREATE_ROOM(11),
    READ_ROOM(12),
    UPDATE_ROOM(13),
    DELETE_ROOM(14),
    CREATE_CUSTOMER(15),
    READ_CUSTOMER(16),
    UPDATE_CUSTOMER(17),
    DELETE_CUSTOMER(18),
    CREATE_RESERVATION(19),
    READ_RESERVATION(20),
    UPDATE_RESERVATION(21),
    DELETE_RESERVATION(22),
    CANCEL_RESERVATION(23);
    
    private final Integer id;
    
    private Task(Integer id) {
        this.id = id;
    }
    
    public Integer getId() {
        return this.id;
    }
}
