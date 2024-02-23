
package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.UserDto;
import com.sunrisehms.dto.UserLoginDto;
import com.sunrisehms.entity.PrivilegeEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.exception.WrongPassword;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.custom.PrivilegeRepository;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class LoginServiceImpl implements LoginService{

    UserRepository userRepository = (UserRepository)RepositoryFactory.getInstance().getRepository(RepositoryType.USER);
    PrivilegeRepository privilegeRepository = (PrivilegeRepository)RepositoryFactory.getInstance().getRepository(RepositoryType.PRIVILEGE);
    
    @Override
    public UserDto login(UserLoginDto userLoginDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        
        try {
            UserEntity user = userRepository.getByUserName(session, userLoginDto.getUserName());
            if (user != null) {
                
                if (userLoginDto.getPassword().equals(user.getPassword())) {
                    
                    List<PrivilegeEntity> privileges = privilegeRepository.getByUser(session, user);                    
                    UserDto userDto = new UserDto();
                    userDto.setId(user.getId());
                    userDto.setFirstName(user.getName().getFirstName());
                    userDto.setLastName(user.getName().getLastName());
                    userDto.setUserName(user.getUserName());
                    if(privileges.isEmpty()) {
                        userDto.setPrivileges(new ArrayList<>());
                    } else {
                        List<Integer> privilegeIds = new ArrayList<>();
                        privileges.forEach(privilege -> privilegeIds.add(privilege.getId().getTask().getId()));
                        userDto.setPrivileges(privilegeIds);
                    }
                    
//                    add log statement
                    
                    transaction.commit();
                    
                    return userDto;
                    
                } else {
                    throw new WrongPassword("password is incorrect");
                }
                
            } else {
                return null;
            }
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
    
}
