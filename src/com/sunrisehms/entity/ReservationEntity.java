
package com.sunrisehms.entity;

import java.math.BigDecimal;
import java.util.Date;
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
@Table(name = "reservation")
public class ReservationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "start", nullable = false)
    private Date start;
    
    @Column(name = "end", nullable = false)
    private Date end;
    
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_status_id", nullable = false)
    private ReservationStatusEntity reservationStatusEntity;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerEntity customerEntity;
    
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id", nullable = false)
    private ReservationPackageEntity reservationPackageEntity;
    
    @Transient
    @OneToMany(mappedBy = "reservationEntity", targetEntity = ReservedRoomEntity.class)
    private List<ReservedRoomEntity> reservedRoomEntitys;
}
