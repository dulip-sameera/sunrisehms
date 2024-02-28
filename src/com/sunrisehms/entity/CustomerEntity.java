/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.entity;

import com.sunrisehms.entity.embedded.CustomerName;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class CustomerEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    private CustomerName customerName;
    
    @Column(name = "nic", length = 12, unique = true, nullable = false)
    private String nic;
    
    @Column(name = "phone", length = 10, unique = true, nullable = false)
    private String phone;
    
    @Column(name = "email", unique = true, nullable = true)
    private String email;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_status_id", nullable = false)
    private CustomerStatus customerStatus;
    
    @Transient
    @OneToMany(mappedBy = "customerEntity", targetEntity = ReservationEntity.class)
    private List<ReservationEntity> reservationEntitys;
}
