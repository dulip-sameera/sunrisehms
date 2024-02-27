package com.sunrisehms.service.custom.impl;

import com.sunrisehms.dto.CustomerDto;
import com.sunrisehms.entity.CustomerEntity;
import com.sunrisehms.entity.LogEntity;
import com.sunrisehms.entity.TaskEntity;
import com.sunrisehms.entity.UserEntity;
import com.sunrisehms.entity.embedded.CustomerName;
import com.sunrisehms.enums.CustomerStatus;
import com.sunrisehms.enums.RepositoryType;
import com.sunrisehms.enums.Task;
import com.sunrisehms.repository.RepositoryFactory;
import com.sunrisehms.repository.TaskRepository;
import com.sunrisehms.repository.custom.CustomerRepository;
import com.sunrisehms.repository.custom.CustomerStatusRepository;
import com.sunrisehms.repository.custom.LogRepository;
import com.sunrisehms.repository.custom.UserRepository;
import com.sunrisehms.service.custom.CustomerService;
import com.sunrisehms.util.SessionFactoryConfiguration;
import com.sunrisehms.util.UserSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerServiceImpl implements CustomerService {

    CustomerStatusRepository customerStatusRepository = (CustomerStatusRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.CUSTOMER_STATUS);
    CustomerRepository customerRepository = (CustomerRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.CUSTOMER);

    UserRepository userRepository = (UserRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.USER);
    TaskRepository taskRepository = (TaskRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.TASK);
    LogRepository logRepository = (LogRepository) RepositoryFactory.getInstance().getRepository(RepositoryType.LOG);

    @Override
    public Long save(CustomerDto customerDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setCustomerName(new CustomerName(customerDto.getFirstName(), customerDto.getMiddleName(), customerDto.getLastName()));
            customerEntity.setCustomerStatus(customerStatusRepository.get(session, CustomerStatus.ACTIVE.getId()));
            customerEntity.setNic(customerDto.getNic());
            customerEntity.setPhone(customerDto.getPhone());
            customerEntity.setEmail(customerDto.getEmail());

            Long id = customerRepository.save(session, customerEntity);
            if (id == null) {
                return null;
            }

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.CREATE_CUSTOMER.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

            return id;

        } catch (Exception e) {

            transaction.rollback();
            throw new Exception("Customer Save Failed");

        }
    }

    @Override
    public void update(CustomerDto customerDto) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            CustomerEntity customerEntity = customerRepository.get(session, customerDto.getId());

            customerEntity.setCustomerName(new CustomerName(customerDto.getFirstName(), customerDto.getMiddleName(), customerDto.getLastName()));
            customerEntity.setCustomerStatus(customerStatusRepository.get(session, customerDto.getStatus()));
            customerEntity.setNic(customerDto.getNic());
            customerEntity.setPhone(customerDto.getPhone());
            customerEntity.setEmail(customerDto.getEmail());

            customerRepository.update(session, customerEntity);

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.UPDATE_CUSTOMER.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Customer update Failed");
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {

            CustomerEntity customerEntity = customerRepository.get(session, id);
            customerEntity.setCustomerStatus(customerStatusRepository.get(session, CustomerStatus.DELETED.getId()));

            customerRepository.update(session, customerEntity);

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.DELETE_CUSTOMER.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Customer delete Failed");
        }
    }

    @Override
    public CustomerDto get(Long id) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            CustomerEntity customerEntity = customerRepository.get(session, id);

            if (customerEntity == null) {
                return null;
            }

            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customerEntity.getId());
            customerDto.setFirstName(customerEntity.getCustomerName().getFirstName());
            customerDto.setMiddleName(customerEntity.getCustomerName().getMiddleName());
            customerDto.setLastName(customerEntity.getCustomerName().getLastName());
            customerDto.setNic(customerEntity.getNic());
            customerDto.setPhone(customerEntity.getPhone());
            customerDto.setEmail(customerEntity.getEmail());
            customerDto.setStatus(customerEntity.getCustomerStatus().getId());

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.READ_CUSTOMER.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

            return customerDto;

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Customer retrieve Failed");
        }
    }

    @Override
    public CustomerDto getByNIC(String nic) throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            CustomerEntity customerEntity = customerRepository.getByNIC(session, nic);

            if (customerEntity == null) {
                return null;
            }

            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customerEntity.getId());
            customerDto.setFirstName(customerEntity.getCustomerName().getFirstName());
            customerDto.setMiddleName(customerEntity.getCustomerName().getMiddleName());
            customerDto.setLastName(customerEntity.getCustomerName().getLastName());
            customerDto.setNic(customerEntity.getNic());
            customerDto.setPhone(customerEntity.getPhone());
            customerDto.setEmail(customerEntity.getEmail());
            customerDto.setStatus(customerEntity.getCustomerStatus().getId());

            // log
            LogEntity log = new LogEntity();
            log.setDateTime(new Date());
            UserEntity user = userRepository.get(session, UserSession.getInstance().getUser().getId());
            log.setUser(user);
            TaskEntity task = taskRepository.get(session, Task.READ_CUSTOMER.getId());
            log.setTask(task);
            logRepository.save(session, log);

            transaction.commit();

            return customerDto;

        } catch (Exception e) {
            transaction.rollback();
            throw new Exception("Customer retrieve Failed");
        }
    }

    @Override
    public List<CustomerDto> getAll() throws Exception {
        Session session = SessionFactoryConfiguration.getInstance().getSession();

        List<CustomerEntity> customerEntitys = customerRepository.getAll(session);
        List<CustomerDto> customerDtos = new ArrayList<>();

        for (CustomerEntity customerEntity : customerEntitys) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customerEntity.getId());
            customerDto.setFirstName(customerEntity.getCustomerName().getFirstName());
            customerDto.setMiddleName(customerEntity.getCustomerName().getMiddleName());
            customerDto.setLastName(customerEntity.getCustomerName().getLastName());
            customerDto.setNic(customerEntity.getNic());
            customerDto.setPhone(customerEntity.getPhone());
            customerDto.setEmail(customerEntity.getEmail());
            customerDto.setStatus(customerEntity.getCustomerStatus().getId());
            
            customerDtos.add(customerDto);
        }
        
        return customerDtos;
    }

}
