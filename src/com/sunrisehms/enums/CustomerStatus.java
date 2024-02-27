
package com.sunrisehms.enums;

public enum CustomerStatus {
    ACTIVE(1, "ACTIVE"),DELETED(2,"DELETED");
    
    private Integer id;
    private String name;
    
    private CustomerStatus(Integer id, String name) {
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
