
package com.sunrisehms.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTableRow {
    private Long id;
    private String fullName;
    private String nic;
    private String phone;
    private String email;
    private String status;
}
