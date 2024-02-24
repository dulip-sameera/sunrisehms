package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.UserDto;
import com.sunrisehms.dto.UserLoginDto;
import com.sunrisehms.entity.LogEntity;
import com.sunrisehms.entity.PrivilegeEntity;
import com.sunrisehms.entity.TaskEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.enums.Task;
import com.sunrisehms.exception.FialedToSaveTheLogException;
import com.sunrisehms.exception.WrongPassword;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.custom.LogRepository;
import com.sunrisehms.repository.custom.PrivilegeRepository;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.service.custom.LoginService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import com.sunrisehms.util.UserSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoginServiceImpl implements LoginService {

    UserRepository userRepository = (UserRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.USER);
    PrivilegeRepository privilegeRepository = (PrivilegeRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.PRIVILEGE);
    LogRepository logRepository = (LogRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.LOG);

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
                    userDto.setJobTitle(user.getJobTitle());
                    if (privileges.isEmpty()) {
                        userDto.setPrivileges(new ArrayList<>());
                    } else {
                        List<Integer> privilegeIds = new ArrayList<>();
                        privileges.forEach(privilege -> privilegeIds.add(privilege.getId().getTask().getId()));
                        userDto.setPrivileges(privilegeIds);
                    }

//                    add log statement
                    LogEntity log = new LogEntity();
                    log.setDateTime(new Date());
                    log.setUser(user);
                    TaskEntity task = getTask(Task.LOGIN, privileges);
                    log.setTask(task);

                    Long id = logRepository.save(session, log);
                    if (id != null) {
                        transaction.commit();
                        return userDto;
                    } else {
                        transaction.rollback();
                        throw new FialedToSaveTheLogException("Failed saving the log");
                    }

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

    @Override
    public void logout() throws Exception {
        UserDto user = UserSession.getInstance().getUser();
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();
        try {
            UserEntity userEntity = userRepository.getByUserName(session, user.getUserName());
            List<PrivilegeEntity> privileges = privilegeRepository.getByUser(session, userEntity);
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            log.setUser(userEntity);
            TaskEntity task = getTask(Task.LOGOUT, privileges);
            log.setTask(task);

            Long id = logRepository.save(session, log);
            if (id != null) {
                transaction.commit();
            } else {
                transaction.rollback();
                throw new FialedToSaveTheLogException("Failed saving the log");
            }

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    private TaskEntity getTask(Task task, List<PrivilegeEntity> privileges) {
        for (PrivilegeEntity privilege : privileges) {
            if (privilege.getId().getTask().getId().equals(task.getId())) {
                return privilege.getId().getTask();
            }
        }
        return null;
    }

}
