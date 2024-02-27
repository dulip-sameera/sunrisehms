
package com.sunrisehms.util;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCategoryTableRow {
    private Integer id;
    private String name;
    private Integer noOfBeds;
    private BigDecimal price;
    private String ac;
}
