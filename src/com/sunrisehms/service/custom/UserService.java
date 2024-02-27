
package com.sunrisehms.service.custom;

import com.sunrisehms.dto.UserDto;
import com.sunrisehms.service.Service;
import java.util.List;

public interface UserService extends Service{
    Long save(UserDto userDto) throws Exception;
    void update(UserDto userDto) throws Exception;
    void delete(Long id) throws Exception;
    UserDto get(Long id) throws Exception;
    List<UserDto> getAll() throws Exception;
}
