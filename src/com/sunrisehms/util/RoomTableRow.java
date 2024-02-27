
package com.sunrisehms.util;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTableRow {
    private Long id;
    private Integer roomNo;
    private Integer floor;
    private String category;
    private BigDecimal price;
    private String status;
}
