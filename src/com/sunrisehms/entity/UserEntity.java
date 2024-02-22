
package com.sunrisehms.entity;

import com.sunrisehms.entity.embedded.UserAddress;
import com.sunrisehms.entity.embedded.UserName;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private UserName name;
    
    private UserAddress address;
    
    @Column(name = "nic", nullable = false, unique = true)
    private String nic;
    
    @Column(name = "job_title", nullable = false)
    private String jobTitle;
    
    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_status_id", nullable = false)
    private UserStatusEntity userStatus;
    
    @Transient
    @OneToMany(mappedBy = "user", targetEntity = PrivilegeEntity.class)
    private List<PrivilegeEntity> privileges;
    
    @Transient
    @OneToMany(mappedBy = "user", targetEntity = LogEntity.class)
    private List<LogEntity> logs;
            
}
