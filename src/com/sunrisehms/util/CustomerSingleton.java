/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.util;

/**
 *
 * @author User
 */
public class CustomerSingleton {
    private static CustomerSingleton customerSingleton;
    
    private CustomerTableRow customerTableRow;
    
    private CustomerSingleton() {}
    
    public static CustomerSingleton getInstance() {
        if (customerSingleton == null) {
            customerSingleton = new CustomerSingleton();
        }
        
        return customerSingleton;
    }
    
    public void setCustomer(CustomerTableRow customerTableRow) {
        this.customerTableRow = customerTableRow;
    }
    
    public CustomerTableRow getCustomer() {
        return this.customerTableRow;
    }
}
