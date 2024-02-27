
package com.sunrisehms.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id;
    private Integer roomNo;
    private Integer floor;
    private BigDecimal price;
    private Integer roomStatus;
    private Integer roomCategory;
}
