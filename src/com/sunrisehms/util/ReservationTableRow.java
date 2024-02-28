
package com.sunrisehms.util;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationTableRow {
    private Long id;
    private String customerNic;
    private String contact;
    private Date from;
    private Date to;
    private String status;
}
