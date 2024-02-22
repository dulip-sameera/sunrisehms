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
public class UserAddress {
    
    @Column(name = "street1", nullable = false)
    private String street1;
    
    @Column(name = "street2", nullable = true)
    private String street2;
    
    @Column(name = "city", nullable = false)
    private String city;
    
    @Column(name = "province", nullable = false)
    private String province;
    
    @Column(name = "postal_code", nullable = false)
    private String postalCode;
    
}
