
package com.sunrisehms.entity.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CustomerName {
    @Column(name = "first_name", nullable = false)
    private String firstName;
    
    @Column(name = "middle_name", nullable = true)
    private String middleName;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
}
