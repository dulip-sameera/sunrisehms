/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.util;

/**
 *
 * @author User
 */
public class ReservationSingleton {
    private static ReservationSingleton reservationSingleton;
    
    private ReservationTableRow reservationTableRow;
    
    private ReservationSingleton() {}
    
    public static ReservationSingleton getInstance() {
        if (reservationSingleton == null) {
            reservationSingleton = new ReservationSingleton();
        }
        
        return reservationSingleton;
    }
    
    public void setReservation(ReservationTableRow reservationTableRow) {
        this.reservationTableRow = reservationTableRow;
    }
    
    public ReservationTableRow getReservation() {
        return this.reservationTableRow;
    }
}
