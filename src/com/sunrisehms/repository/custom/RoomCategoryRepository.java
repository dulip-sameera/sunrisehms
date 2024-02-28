
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.RoomCategoryEntity;
import com.sunrisehms.repository.CrudRepository;
import org.hibernate.Session;

public interface RoomCategoryRepository extends CrudRepository<RoomCategoryEntity, Integer>{
    RoomCategoryEntity getByName(Session session, String name) throws Exception;
}
