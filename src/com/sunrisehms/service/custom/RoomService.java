package com.sunrisehms.service.custom;

import com.sunrisehms.dto.RoomCategoryDto;
import com.sunrisehms.dto.RoomDto;
import com.sunrisehms.service.Service;
import java.util.List;

public interface RoomService extends Service{
    Long save(RoomDto roomDto) throws Exception;
    void update(RoomDto roomDto) throws Exception;
    void delete(Long id) throws Exception;
    RoomDto get(Long id) throws Exception;
    List<RoomDto> getAll() throws Exception;
    RoomDto getByRoomNo(Integer roomNo) throws Exception;
    List<RoomDto> getAllAvailableRoomsByCategory(RoomCategoryDto roomCategoryDto) throws Exception;
    List<RoomDto> getAllAvailableRooms() throws Exception;
}
