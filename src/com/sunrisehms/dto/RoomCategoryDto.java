
package com.sunrisehms.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCategoryDto {
    private Integer id;
    private String name;
    private Integer noOfBeds;
    private BigDecimal price;
    private Boolean ac;
}
