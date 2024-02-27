
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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room_category")
public class RoomCategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "no_of_beds", nullable = false)
    private Integer noOfBeds;
    
    @Column(name = "ac", nullable = false)
    private Boolean ac;
    
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    @Transient
    @OneToMany(mappedBy = "roomCategory", targetEntity = RoomEntity.class)
    private List<RoomEntity> rooms;
}
