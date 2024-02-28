
package com.sunrisehms.entity;

import com.sunrisehms.entity.embedded.ReservedRoomId;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reserved_room")
public class ReservedRoomEntity {
    @EmbeddedId
    private ReservedRoomId reservedRoomId;
    
    @Column(name = "check_in", nullable = true)
    private Date checkIn;
    
    @Column(name = "check_out", nullable = true)
    private Date checkOut;
}
