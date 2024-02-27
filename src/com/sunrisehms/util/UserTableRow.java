
package com.sunrisehms.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTableRow {
    private Long id;
    private String firstName;
    private String lastName;
    private String status;
    private String userName;
    private String jobTitle;
    
}
