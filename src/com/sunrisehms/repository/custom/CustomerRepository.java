
package com.sunrisehms.repository.custom;

import com.sunrisehms.entity.CustomerEntity;
import com.sunrisehms.repository.CrudRepository;
import org.hibernate.Session;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long>{
    CustomerEntity getByNIC(Session session, String nic) throws Exception;
}
