
package com.sunrisehms.service.custom;

import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.service.Service;
import java.util.List;

public interface RoomCategoryService extends Service{
    Integer save(RoomCategoryDto roomCategoryDto) throws Exception;
    void update(RoomCategoryDto roomCategoryDto) throws Exception;
    void delete(Integer id) throws Exception;
    RoomCategoryDto get(Integer id) throws Exception;
    List<RoomCategoryDto> getAll() throws Exception;
    RoomCategoryDto getByName(String name) throws Exception;
}
