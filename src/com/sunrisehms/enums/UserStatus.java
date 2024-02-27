
package com.sunrisehms.enums;

public enum UserStatus {
    
    ACTIVE(1, "ACTIVE"),DELETE(2, "DELETE");
    
    private Integer id;
    private String name;
    
    private UserStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
