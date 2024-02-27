
package com.sunrisehms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nic;
    private String phone;
    private String email;
    private Integer status;
}
