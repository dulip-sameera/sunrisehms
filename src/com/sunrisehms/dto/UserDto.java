
package com.sunrisehms.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String jobTitle;
    private String password;
    private Integer status;
    private List<Integer> privileges;
}
