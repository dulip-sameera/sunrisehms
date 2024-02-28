/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "package")
public class ReservationPackageEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    @Transient
    @OneToMany(mappedBy = "reservationPackageEntity", targetEntity = ReservationEntity.class)
    List<ReservationEntity> reservationEntitys;
}
