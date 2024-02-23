
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.repository.CrudRepository;
import java.util.List;
import org.hibernate.Session;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
    UserEntity getByUserName(Session session,String username) throws Exception;
}
