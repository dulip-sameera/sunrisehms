package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.UserDto;
import com.sunrisehms.entity.LogEntity;
import com.sunrisehms.entity.PrivilegeEntity;
import com.sunrisehms.entity.TaskEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.entity.UserStatusEntity;
import com.sunrisehms.entity.embedded.PrivilegeID;
import com.sunrisehms.entity.embedded.UserName;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.enums.Task;
import com.sunrisehms.enums.UserStatus;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.TaskRepository;
import com.sunrisehms.repository.custom.LogRepository;
import com.sunrisehms.repository.custom.PrivilegeRepository;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.repository.custom.UserStatusRepository;
import com.sunrisehms.service.custom.UserService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import com.sunrisehms.util.UserSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserServiceImpl implements UserService {

    UserRepository userRepository = (UserRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.USER);
    UserStatusRepository userStatusRepository = (UserStatusRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.USER_STATUS);
    TaskRepository taskRepository = (TaskRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.TASK);
    LogRepository logRepository = (LogRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.LOG);
    PrivilegeRepository privilegeRepository = (PrivilegeRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.PRIVILEGE);

    @Override
    public Long save(UserDto userDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(new UserName(userDto.getFirstName(), userDto.getLastName()));
            userEntity.setPassword(userDto.getPassword());
            userEntity.setUserName(userDto.getUserName());
            userEntity.setJobTitle(userDto.getJobTitle());

            UserStatusEntity userStatus = userStatusRepository.get(session, userDto.getStatus());
            userEntity.setUserStatus(userStatus);

            Long newUserId = userRepository.save(session, userEntity);

            if (newUserId == null) {
                throw new Exception("user creation failed");
            }

            System.out.println(newUserId);

            // setting privileges
            UserEntity newUser = userRepository.get(session, newUserId);
            List<Integer> privileges = userDto.getPrivileges();
            List<PrivilegeEntity> privilegeEntitys = new ArrayList<>();
            for (Integer privilege : privileges) {
                PrivilegeEntity newPrivilege = new PrivilegeEntity();
                TaskEntity task = taskRepository.get(session, privilege);
                PrivilegeID id = new PrivilegeID(newUser, task);
                newPrivilege.setId(id);
                privilegeEntitys.add(newPrivilege);
            }

            privilegeEntitys.forEach(System.out::println);

            for (PrivilegeEntity privilegeEntity : privilegeEntitys) {
                privilegeRepository.save(session, privilegeEntity);
            }

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.CREATE_USER.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();
            return newUserId;

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("User Save Failed");
        }
    }

    @Override
    public void update(UserDto userDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            UserEntity userEntity = userRepository.get(session, userDto.getId());

            List<PrivilegeEntity> oldPrivileges = privilegeRepository.getByUser(session, userEntity);

            userEntity.setName(new UserName(userDto.getFirstName(), userDto.getLastName()));
            userEntity.setPassword(userDto.getPassword());
            userEntity.setUserName(userDto.getUserName());
            userEntity.setJobTitle(userDto.getJobTitle());

            UserStatusEntity userStatus = userStatusRepository.get(session, userDto.getStatus());
            userEntity.setUserStatus(userStatus);
            userRepository.update(session, userEntity);

            List<Integer> privileges = userDto.getPrivileges();
            List<PrivilegeEntity> privilegeEntitys = new ArrayList<>();
            for (Integer privilege : privileges) {
                PrivilegeEntity newPrivilege = new PrivilegeEntity();
                TaskEntity task = taskRepository.get(session, privilege);
                PrivilegeID id = new PrivilegeID(userEntity, task);
                newPrivilege.setId(id);
                privilegeEntitys.add(newPrivilege);
            }

            // delte old privileges
            for (PrivilegeEntity oldPrivilege : oldPrivileges) {
                privilegeRepository.delete(session, oldPrivilege);
            }

            // save new privileges
            for (PrivilegeEntity privilegeEntity : privilegeEntitys) {
                privilegeRepository.save(session, privilegeEntity);
            }

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.UPDATE_USER.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("User Update Failed");
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            UserEntity userEntity = userRepository.get(session, id);
            UserStatusEntity userStatus = userStatusRepository.get(session, UserStatus.DELETE.getId());
            userEntity.setUserStatus(userStatus);
            userRepository.update(session, userEntity);

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.DELETE_USER.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("User Deletion Failed");
        }
    }

    @Override
    public UserDto get(Long id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        UserEntity user = userRepository.get(session, id);

        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getName().getFirstName());
        userDto.setLastName(user.getName().getLastName());
        userDto.setJobTitle(user.getJobTitle());
        userDto.setPassword(user.getPassword());
        userDto.setUserName(user.getUserName());
        userDto.setStatus(user.getUserStatus().getId());

        List<Integer> privileges = new ArrayList<>();
        List<PrivilegeEntity> userPrivileges = privilegeRepository.getByUser(session, user);
        for (PrivilegeEntity privilege : userPrivileges) {
            privileges.add(privilege.getId().getTask().getId());
        }
        userDto.setPrivileges(privileges);

        // log
        LogEntity log = new LogEntity();
        log.setDateTime(new Date());
        UserEntity loggedUser = userRepository.get(session, UserSession.getInstance().getUser().getId());
        log.setUser(loggedUser);
        TaskEntity task = taskRepository.get(session, Task.READ_USER.getId());
        log.setTask(task);
        logRepository.save(session, log);

        return userDto;
    }

    @Override
    public List<UserDto> getAll() throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        List<UserEntity> users = userRepository.getAll(session);
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getName().getFirstName());
            userDto.setLastName(user.getName().getLastName());
            userDto.setJobTitle(user.getJobTitle());
            userDto.setPassword(user.getPassword());
            userDto.setUserName(user.getUserName());
            userDto.setStatus(user.getUserStatus().getId());

            List<Integer> privileges = new ArrayList<>();
            List<PrivilegeEntity> userPrivileges = privilegeRepository.getByUser(session, user);
            for (PrivilegeEntity privilege : userPrivileges) {
                privileges.add(privilege.getId().getTask().getId());
            }
            userDto.setPrivileges(privileges);
            userDtos.add(userDto);
        }

        return userDtos;
    }

}
