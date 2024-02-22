
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.PrivilegeEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.entity.embedded.PrivilegeID;
import com.sunrisehms.repository.CrudRepository;
import java.util.List;
import org.hibernate.Session;

public interface PrivilegeRepository extends CrudRepository<PrivilegeEntity, PrivilegeID>{
    List<PrivilegeEntity> getByUser(Session session,UserEntity user) throws Exception;
}
