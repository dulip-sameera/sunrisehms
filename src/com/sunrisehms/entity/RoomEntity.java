package com.sunrisehms.entity;

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class RoomEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "room_no", nullable = false)
    private Integer roomNo;
    
    @Column(name = "floor", nullable = false)
    private Integer floor;
    
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_status_id", nullable = false)
    private RoomStatusEntity roomStatus;
    
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_category_id", nullable = false)
    private RoomCategoryEntity roomCategory;
    
}
