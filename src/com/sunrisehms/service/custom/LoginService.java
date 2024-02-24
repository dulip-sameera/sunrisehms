
package com.sunrisehms.service.custom;

import com.sunrisehms.dto.UserDto;
import com.sunrisehms.dto.UserLoginDto;
import com.sunrisehms.service.Service;

public interface LoginService extends Service{
    
    UserDto login(UserLoginDto userLoginDto) throws Exception;
    void logout() throws Exception;
}
