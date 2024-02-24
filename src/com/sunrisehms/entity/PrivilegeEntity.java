
package com.sunrisehms.entity;

import com.sunrisehms.entity.embedded.PrivilegeID;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "privilege")
public class PrivilegeEntity {
    
    @EmbeddedId
    private PrivilegeID id;
}
