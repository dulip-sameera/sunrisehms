
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.RoomCategoryEntity;
import com.sunrisehms.entity.RoomEntity;
import com.sunrisehms.entity.RoomStatusEntity;
import com.sunrisehms.repository.CrudRepository;
import java.util.List;
import org.hibernate.Session;

public interface RoomRepository extends CrudRepository<RoomEntity, Long>{
    RoomEntity getByRoomNo(Session session ,Integer roomNo) throws Exception;
    List<RoomEntity> getAllByStatusAndCategory(Session session, RoomCategoryEntity categoryEntity, RoomStatusEntity roomStatus) throws Exception;
    List<RoomEntity> getAllByStatus(Session session, RoomStatusEntity roomStatusEntity) throws Exception;
            
}
