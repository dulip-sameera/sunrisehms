package com.sunrisehms.enums;

public enum RoomStatus {
    AVAILABLE(1, "AVAILABLE"),
    RESERVED(2,"RESERVED"),
    OCCUPIED(3,"OCCUPIED"),
    MAINTAINANCE(4,"MAINTAINANCE"),
    DELETED(5, "DELETED");
    
    private Integer id;
    private String name;
    
    private RoomStatus(Integer id, String name) {
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
