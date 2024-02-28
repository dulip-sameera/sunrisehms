/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.sunrisehms.enums;

/**
 *
 * @author User
 */
public enum ReservationStatus {
    
    SHOW(1, "SHOW"),
    NO_SHOW(2,"NO SHOW"),
    PENDING(3,"PENDING"),
    CONFIRMED(4,"CONFIRMED"),
    CANCELLED(5, "CANCELLED"),
    DELETED(6,"DELETED");
    
    private Integer id;
    private String name;
    
    private ReservationStatus(Integer id, String name) {
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
