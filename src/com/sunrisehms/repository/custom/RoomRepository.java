
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.RoomEntity;
import com.sunrisehms.repository.CrudRepository;
import org.hibernate.Session;

public interface RoomRepository extends CrudRepository<RoomEntity, Long>{
    RoomEntity getByRoomNo(Session session ,Integer roomNo) throws Exception;
}
