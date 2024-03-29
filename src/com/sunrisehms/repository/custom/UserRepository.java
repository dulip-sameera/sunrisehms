
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.repository.CrudRepository;
import org.hibernate.Session;

public interface UserRepository extends CrudRepository<UserEntity, Long>{
    UserEntity getByUserName(Session session,String username) throws Exception;
    UserEntity getById(Session session, Long id) throws Exception;
}
